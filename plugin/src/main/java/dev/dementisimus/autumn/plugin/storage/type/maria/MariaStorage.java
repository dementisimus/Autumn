/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin.storage.type.maria;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.dementisimus.autumn.api.callback.DoubleCallback;
import dev.dementisimus.autumn.api.callback.SingleCallback;
import dev.dementisimus.autumn.api.callback.TripleCallback;
import dev.dementisimus.autumn.api.storage.Storage;
import dev.dementisimus.autumn.api.storage.property.StorageProperty;
import dev.dementisimus.autumn.api.storage.property.StorageUpdateProperty;
import dev.dementisimus.autumn.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.api.storage.type.StorageType;
import dev.dementisimus.autumn.plugin.setup.state.MainSetupStates;
import dev.dementisimus.autumn.plugin.storage.CustomStorage;
import lombok.SneakyThrows;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.*;
import java.util.*;

public class MariaStorage implements StorageType {

    private final HikariDataSource hikariDataSource;
    private final Storage.Type storageType;

    public MariaStorage(CustomStorage storage) {
        this.storageType = storage.getType();

        String driver = this.storageType.equals(Storage.Type.MARIADB) ? "com.mysql.cj.jdbc.Driver" : "org.sqlite.JDBC";

        String host = MainSetupStates.MARIADB_HOST.asString();
        int port = MainSetupStates.MARIADB_PORT.asInteger();
        String databaseName = MainSetupStates.DATABASE.asString();
        String user = MainSetupStates.MARIADB_USER.asString();
        String password = MainSetupStates.MARIADB_PASSWORD.asString();

        String sqliteFilePath = MainSetupStates.SQLITE_FILE_PATH.asString();

        String jdbcUrl = this.storageType.equals(Storage.Type.MARIADB) ? "jdbc:mysql://" + host + ":" + port + "/" + databaseName : "jdbc:sqlite:" + sqliteFilePath;

        this.registerDriver(driver);

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setLeakDetectionThreshold(60000);

        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(jdbcUrl);
        if (this.storageType.equals(Storage.Type.MARIADB)) {
            hikariConfig.setUsername(user);
            hikariConfig.setPassword(password);
        }

        hikariConfig.addDataSourceProperty("serverTimezone", Calendar.getInstance().getTimeZone().getID());
        hikariConfig.setConnectionTestQuery("SELECT 1;");

        this.hikariDataSource = new HikariDataSource(hikariConfig);

        for (StorageSourceProperty storageSourceProperty : storage.getStorageSourceProperties()) {
            StringBuilder fields = new StringBuilder();

            storageSourceProperty.fields().forEach((fieldName, fieldType) -> {
                if (!fields.isEmpty()) {
                    fields.append(" ");
                }
                fields.append(fieldName).append(" ").append(fieldType).append(", ");
            });

            String sql = "CREATE TABLE IF NOT EXISTS " + storageSourceProperty.name() + " (" + fields.substring(0, fields.length() - 2) + ");";
            this.prepareStatement(sql, ExecuteAction.PLAIN, (executed, resultSet, rowCount) -> {
            });
        }
    }

    @Override
    public void read(StorageSourceProperty storageSourceProperty, StorageProperty storageProperty, SingleCallback<@Nullable Document> documentCallback) {
        Document document = new Document();
        List<String> fieldKeys = new ArrayList<>(storageSourceProperty.fields().keySet());

        String sql = "SELECT " + this.parseFieldKeysToSQLFields(fieldKeys) + " FROM " + storageSourceProperty.name() + " WHERE " + storageProperty.fieldName() + " = ?;";
        Map<Integer, Object> parameters = new HashMap<>();

        parameters.put(1, storageProperty.fieldValue());

        this.prepareStatement(sql, parameters, ExecuteAction.QUERY, (executed, resultSet) -> {
            try {

                while (resultSet.next()) {
                    for (String fieldKey : fieldKeys) {
                        document.put(fieldKey, resultSet.getObject(fieldKey));
                    }
                }

                documentCallback.done(document);
            } catch (Exception ignored) {
            }
        });
    }

    @Override
    public void list(StorageSourceProperty storageSourceProperty, SingleCallback<@NotNull List<Document>> listDocumentCallback) {
        List<Document> documents = new ArrayList<>();

        String sql = "SELECT * FROM " + storageSourceProperty.name() + ";";

        this.prepareStatement(sql, ExecuteAction.QUERY, (executed, resultSet, rowCount) -> {
            try {

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columns = resultSetMetaData.getColumnCount();

                while (resultSet.next()) {
                    Document document = new Document();

                    for (int index = 1; index <= columns; index++) {
                        document.put(resultSetMetaData.getColumnName(index), resultSet.getObject(index));
                    }

                    documents.add(document);
                }

                listDocumentCallback.done(documents);
            } catch (Exception ignored) {
            }
        });
    }

    @Override
    public void write(StorageSourceProperty storageSourceProperty, Document document, SingleCallback<@NotNull Boolean> booleanCallback) {
        StringBuilder keys = new StringBuilder();
        StringBuilder keyValues = new StringBuilder();
        Map<Integer, Object> parameters = new HashMap<>();

        List<String> keySet = new ArrayList<>(document.keySet());
        for (String key : keySet) {
            keys.append(key);
            keyValues.append("?");
            parameters.put((keySet.indexOf(key) + 1), document.get(key));

            if (!(keySet.indexOf(key) == (keySet.size() - 1))) {
                keys.append(",");
                keyValues.append(",");
            }
        }

        String sql = "INSERT INTO " + storageSourceProperty.name() + " (" + keys + ") VALUES (" + keyValues + ");";

        this.prepareStatement(sql, parameters, ExecuteAction.PLAIN, (executed, resultSet) -> booleanCallback.done(executed));
    }

    @Override
    public void update(StorageSourceProperty storageSourceProperty, StorageUpdateProperty storageUpdateProperty, SingleCallback<@NotNull Boolean> booleanCallback) {
        String sql = "UPDATE " + storageSourceProperty.name() + " SET " + storageUpdateProperty.name() + " = ? WHERE " + storageUpdateProperty.fieldName() + "=?;";
        Map<Integer, Object> parameters = new HashMap<>();

        parameters.put(1, storageUpdateProperty.value());
        parameters.put(2, storageUpdateProperty.fieldValue());

        this.prepareStatement(sql, parameters, ExecuteAction.PLAIN, (executed, resultSet) -> booleanCallback.done(executed));
    }

    @Override
    public void delete(StorageSourceProperty storageSourceProperty, StorageProperty storageProperty, SingleCallback<@NotNull Boolean> booleanCallback) {
        String sql = "DELETE FROM " + storageSourceProperty.name() + " WHERE " + storageProperty.fieldName() + " = ?;";
        Map<Integer, Object> parameters = new HashMap<>();

        parameters.put(1, storageProperty.fieldValue());

        this.prepareStatement(sql, parameters, ExecuteAction.PLAIN, (executed, resultSet) -> booleanCallback.done(executed));
    }

    @Override
    public void isPresent(StorageSourceProperty storageSourceProperty, StorageProperty storageProperty, SingleCallback<@NotNull Boolean> booleanCallback) {
        this.read(storageSourceProperty, storageProperty, document -> {
            booleanCallback.done(document != null && !document.isEmpty());
        });
    }

    @Override
    public void close() {
        this.hikariDataSource.close();
    }

    @Override
    public @NotNull String getReadyTranslationProperty() {
        return this.storageType.equals(Storage.Type.MARIADB) ? "autumn.storage.maria.ready" : "autumn.storage.sqlite.ready";
    }

    @SneakyThrows
    private void prepareStatement(String sql, ExecuteAction executeAction, TripleCallback<Boolean, ResultSet, Integer> executionCallback) {
        try (Connection connection = this.hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            switch (executeAction) {
                case PLAIN -> {
                    preparedStatement.execute();
                    executionCallback.done(true, null, null);
                }
                case QUERY -> executionCallback.done(null, preparedStatement.executeQuery(), null);
            }
        }
    }

    @SneakyThrows
    private void prepareStatement(String sql, Map<Integer, Object> parameters, ExecuteAction executeAction, DoubleCallback<Boolean, ResultSet> booleanResultSetBiCallback) {
        try (Connection connection = this.hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            parameters.forEach((key, value) -> {
                try {
                    if (value instanceof String string) {
                        preparedStatement.setString(key, string);
                    } else if (value instanceof Boolean bool) {
                        preparedStatement.setBoolean(key, bool);
                    } else if (value instanceof Integer integer) {
                        preparedStatement.setInt(key, integer);
                    } else if (value instanceof Float fl) {
                        preparedStatement.setFloat(key, fl);
                    } else if (value instanceof Double db) {
                        preparedStatement.setDouble(key, db);
                    } else if (value instanceof Date date) {
                        preparedStatement.setDate(key, date);
                    } else if (value instanceof Time time) {
                        preparedStatement.setTime(key, time);
                    } else if (value instanceof Timestamp timestamp) {
                        preparedStatement.setTimestamp(key, timestamp);
                    } else {
                        preparedStatement.setObject(key, value);
                    }
                } catch (Exception ignored) {
                }
            });

            if (executeAction.equals(ExecuteAction.QUERY)) {
                booleanResultSetBiCallback.done(null, preparedStatement.executeQuery());
                return;
            }
            preparedStatement.execute();
            booleanResultSetBiCallback.done(true, null);
        }
    }

    private String parseFieldKeysToSQLFields(List<String> fieldKeys) {
        StringBuilder sqlFields = new StringBuilder();

        for (String fieldKey : fieldKeys) {
            sqlFields.append(fieldKey);

            if (!(fieldKeys.indexOf(fieldKey) == (fieldKeys.size() - 1))) {
                sqlFields.append(",");
            }
        }

        return sqlFields.toString();
    }

    @SneakyThrows
    private void registerDriver(String driver) {
        DriverManager.registerDriver((Driver) Class.forName(driver).newInstance());
    }

    public enum ExecuteAction {
        PLAIN,
        QUERY
    }
}
