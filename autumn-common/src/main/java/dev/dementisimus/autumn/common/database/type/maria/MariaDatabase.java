package dev.dementisimus.autumn.common.database.type.maria;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.dementisimus.autumn.common.api.callback.AutumnBiCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnTriCallback;
import dev.dementisimus.autumn.common.api.database.Database;
import dev.dementisimus.autumn.common.api.database.property.DataProperty;
import dev.dementisimus.autumn.common.api.database.property.UpdateDataProperty;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import dev.dementisimus.autumn.common.api.database.type.DatabaseType;
import dev.dementisimus.autumn.common.database.DefaultDatabase;
import dev.dementisimus.autumn.common.setup.state.MainSetupStates;
import lombok.SneakyThrows;
import org.bson.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class MariaDatabase @ Autumn
 *
 * @author dementisimus
 * @since 30.11.2021:15:11
 */
public class MariaDatabase implements DatabaseType {

    private final HikariDataSource hikariDataSource;
    private final Database.Type databaseType;

    public MariaDatabase(DefaultDatabase database) {
        this.databaseType = database.getType();

        String driver = this.databaseType.equals(Database.Type.MARIADB) ? "com.mysql.cj.jdbc.Driver" : "org.sqlite.JDBC";

        String host = MainSetupStates.MARIADB_HOST.asString();
        int port = MainSetupStates.MARIADB_PORT.asInteger();
        String databaseName = MainSetupStates.DATABASE.asString();
        String user = MainSetupStates.MARIADB_USER.asString();
        String password = MainSetupStates.MARIADB_PASSWORD.asString();

        String sqliteFilePath = MainSetupStates.SQLITE_FILE_PATH.asString();

        String jdbcUrl = this.databaseType.equals(Database.Type.MARIADB) ? "jdbc:mysql://" + host + ":" + port + "/" + databaseName : "jdbc:sqlite:" + sqliteFilePath;

        this.registerDriver(driver);

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setLeakDetectionThreshold(60000);

        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(jdbcUrl);
        if(this.databaseType.equals(Database.Type.MARIADB)) {
            hikariConfig.setUsername(user);
            hikariConfig.setPassword(password);
        }

        hikariConfig.addDataSourceProperty("serverTimezone", Calendar.getInstance().getTimeZone().getID());
        hikariConfig.setConnectionTestQuery("SELECT 1;");

        this.hikariDataSource = new HikariDataSource(hikariConfig);

        for(DataSourceProperty dataSourceProperty : database.getDataSourceProperties()) {
            StringBuilder fields = new StringBuilder();

            dataSourceProperty.fields().forEach((fieldName, fieldType) -> {
                if(!fields.isEmpty()) {
                    fields.append(" ");
                }
                fields.append(fieldName).append(" ").append(fieldType).append(", ");
            });

            String sql = "CREATE TABLE IF NOT EXISTS " + dataSourceProperty.name() + " (" + fields.substring(0, fields.length() - 2) + ");";
            this.prepareStatement(sql, ExecuteAction.PLAIN, (executed, resultSet, rowCount) -> {});
        }
    }

    @Override
    public void read(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Document> documentCallback) {
        Document document = new Document();
        List<String> fieldKeys = new ArrayList<>(dataSourceProperty.fields().keySet());

        String sql = "SELECT " + this.parseFieldKeysToSQLFields(fieldKeys) + " FROM " + dataSourceProperty.name() + " WHERE " + dataProperty.fieldName() + " = ?;";
        Map<Integer, Object> parameters = new HashMap<>();

        parameters.put(1, dataProperty.fieldValue());

        this.prepareStatement(sql, parameters, ExecuteAction.QUERY, (executed, resultSet) -> {
            try {

                while(resultSet.next()) {
                    for(String fieldKey : fieldKeys) {
                        document.put(fieldKey, resultSet.getObject(fieldKey));
                    }
                }

                documentCallback.done(document);
            }catch(Exception ignored) {}
        });
    }

    @Override
    public void list(DataSourceProperty dataSourceProperty, AutumnCallback<List<Document>> listDocumentCallback) {
        List<Document> documents = new ArrayList<>();

        String sql = "SELECT * FROM " + dataSourceProperty.name() + ";";

        this.prepareStatement(sql, ExecuteAction.QUERY, (executed, resultSet, rowCount) -> {
            try {

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columns = resultSetMetaData.getColumnCount();

                while(resultSet.next()) {
                    Document document = new Document();

                    for(int index = 1; index <= columns; index++) {
                        document.put(resultSetMetaData.getColumnName(index), resultSet.getObject(index));
                    }

                    documents.add(document);
                }

                listDocumentCallback.done(documents);
            }catch(Exception ignored) {}
        });
    }

    @Override
    public void write(DataSourceProperty dataSourceProperty, Document document, AutumnCallback<Boolean> booleanCallback) {
        StringBuilder keys = new StringBuilder();
        StringBuilder keyValues = new StringBuilder();
        Map<Integer, Object> parameters = new HashMap<>();

        List<String> keySet = new ArrayList<>(document.keySet());
        for(String key : keySet) {
            keys.append(key);
            keyValues.append("?");
            parameters.put((keySet.indexOf(key) + 1), document.get(key));

            if(!(keySet.indexOf(key) == (keySet.size() - 1))) {
                keys.append(",");
                keyValues.append(",");
            }
        }

        String sql = "INSERT INTO " + dataSourceProperty.name() + " (" + keys + ") VALUES (" + keyValues + ");";

        this.prepareStatement(sql, parameters, ExecuteAction.PLAIN, (executed, resultSet) -> booleanCallback.done(executed));
    }

    @Override
    public void update(DataSourceProperty dataSourceProperty, UpdateDataProperty updateDataProperty, AutumnCallback<Boolean> booleanCallback) {
        String sql = "UPDATE " + dataSourceProperty.name() + " SET " + updateDataProperty.name() + " = ? WHERE " + updateDataProperty.fieldName() + "=?;";
        Map<Integer, Object> parameters = new HashMap<>();

        parameters.put(1, updateDataProperty.value());
        parameters.put(2, updateDataProperty.fieldValue());

        this.prepareStatement(sql, parameters, ExecuteAction.PLAIN, (executed, resultSet) -> booleanCallback.done(executed));
    }

    @Override
    public void delete(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback) {
        String sql = "DELETE FROM " + dataSourceProperty.name() + " WHERE " + dataProperty.fieldName() + " = ?;";
        Map<Integer, Object> parameters = new HashMap<>();

        parameters.put(1, dataProperty.fieldValue());

        this.prepareStatement(sql, parameters, ExecuteAction.PLAIN, (executed, resultSet) -> booleanCallback.done(executed));
    }

    @Override
    public void isPresent(DataSourceProperty dataSourceProperty, DataProperty dataProperty, AutumnCallback<Boolean> booleanCallback) {
        this.read(dataSourceProperty, dataProperty, document -> {
            booleanCallback.done(document != null && !document.isEmpty());
        });
    }

    @Override
    public void close() {
        this.hikariDataSource.close();
    }

    @Override
    public String readyTranslationProperty() {
        return this.databaseType.equals(Database.Type.MARIADB) ? "autumn.database.maria.ready" : "autumn.database.sqlite.ready";
    }

    @SneakyThrows
    private void prepareStatement(String sql, ExecuteAction executeAction, AutumnTriCallback<Boolean, ResultSet, Integer> executionCallback) {
        try(Connection connection = this.hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            switch(executeAction) {
                case PLAIN -> {
                    preparedStatement.execute();
                    executionCallback.done(true, null, null);
                }
                case QUERY -> executionCallback.done(null, preparedStatement.executeQuery(), null);
            }
        }
    }

    @SneakyThrows
    private void prepareStatement(String sql, Map<Integer, Object> parameters, ExecuteAction executeAction, AutumnBiCallback<Boolean, ResultSet> booleanResultSetBiCallback) {
        try(Connection connection = this.hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            parameters.forEach((key, value) -> {
                try {
                    if(value instanceof String string) {
                        preparedStatement.setString(key, string);
                    }else if(value instanceof Boolean bool) {
                        preparedStatement.setBoolean(key, bool);
                    }else if(value instanceof Integer integer) {
                        preparedStatement.setInt(key, integer);
                    }else if(value instanceof Float fl) {
                        preparedStatement.setFloat(key, fl);
                    }else if(value instanceof Double db) {
                        preparedStatement.setDouble(key, db);
                    }else if(value instanceof Date date) {
                        preparedStatement.setDate(key, date);
                    }else if(value instanceof Time time) {
                        preparedStatement.setTime(key, time);
                    }else if(value instanceof Timestamp timestamp) {
                        preparedStatement.setTimestamp(key, timestamp);
                    }else {
                        preparedStatement.setObject(key, value);
                    }
                }catch(Exception ignored) {}
            });

            if(executeAction.equals(ExecuteAction.QUERY)) {
                booleanResultSetBiCallback.done(null, preparedStatement.executeQuery());
                return;
            }
            preparedStatement.execute();
            booleanResultSetBiCallback.done(true, null);
        }
    }

    private String parseFieldKeysToSQLFields(List<String> fieldKeys) {
        StringBuilder sqlFields = new StringBuilder();

        for(String fieldKey : fieldKeys) {
            sqlFields.append(fieldKey);

            if(!(fieldKeys.indexOf(fieldKey) == (fieldKeys.size() - 1))) {
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
