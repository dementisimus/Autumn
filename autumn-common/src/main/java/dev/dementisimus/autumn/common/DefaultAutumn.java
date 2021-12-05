package dev.dementisimus.autumn.common;

import com.github.derrop.documents.Document;
import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.common.api.Autumn;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnEmptyCallback;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import dev.dementisimus.autumn.common.api.database.Database;
import dev.dementisimus.autumn.common.api.database.property.source.DataSourceProperty;
import dev.dementisimus.autumn.common.api.dependency.AutumnDependency;
import dev.dementisimus.autumn.common.api.dependency.AutumnRepository;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.common.api.i18n.AutumnTranslation;
import dev.dementisimus.autumn.common.api.injection.AutumnInjector;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnCommand;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnListener;
import dev.dementisimus.autumn.common.api.injection.annotation.AutumnSetupListener;
import dev.dementisimus.autumn.common.api.log.AutumnLogging;
import dev.dementisimus.autumn.common.api.server.ServerType;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.configuration.DefaultAutumnConfiguration;
import dev.dementisimus.autumn.common.database.DefaultDatabase;
import dev.dementisimus.autumn.common.debug.SysOut;
import dev.dementisimus.autumn.common.dependency.DefaultAutumnDependency;
import dev.dementisimus.autumn.common.dependency.DefaultAutumnRepository;
import dev.dementisimus.autumn.common.file.DefaultFileDownloader;
import dev.dementisimus.autumn.common.file.DefaultZipFileDownloader;
import dev.dementisimus.autumn.common.i18n.DefaultAutumnTranslation;
import dev.dementisimus.autumn.common.i18n.property.AutumnTranslationProperty;
import dev.dementisimus.autumn.common.injection.DefaultAutumnInjector;
import dev.dementisimus.autumn.common.setup.DefaultSetupManager;
import dev.dementisimus.autumn.common.setup.state.MainSetupStates;
import dev.dementisimus.autumn.common.setup.value.SetupValueManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class DefaultAutumn @ AutumnCommon
 *
 * @author dementisimus
 * @since 22.11.2021:20:33
 */
public abstract class DefaultAutumn implements Autumn {

    @Getter(AccessLevel.PROTECTED) private final Object plugin;
    @Getter private final AutumnTaskExecutor taskExecutor;
    @Getter private final AutumnLogging logging;
    @Getter private final DefaultFileDownloader fileDownloader;

    @Getter private DefaultAutumnInjector injector;
    @Getter @Setter(AccessLevel.PROTECTED) private DefaultSetupManager setupManager;
    @Getter private DefaultZipFileDownloader zipFileDownloader;

    @Getter private Database.Type databaseType;
    @Getter private DefaultDatabase database;
    @Getter private AutumnLanguage defaultLanguage = AutumnLanguage.ENGLISH;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private ClassLoader autumnClassLoader;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private ClassLoader pluginClassLoader;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private SetupValueManager setupValueManager;

    @Getter @Setter(AccessLevel.PROTECTED) private String pluginName;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private String pluginVersion;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private ServerType servertype;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private String serverVersion;

    @Getter private File pluginFolder;
    @Getter private File configurationFile;
    private AutumnCallback<AutumnInjector> initializationCallback;
    private boolean optionalCommands;
    private boolean optionalListeners;

    public DefaultAutumn(Object plugin, AutumnTaskExecutor taskExecutor, AutumnLogging logging) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null!");
        Preconditions.checkNotNull(taskExecutor, "TaskExecutor may not be null!");
        Preconditions.checkNotNull(logging, "Logging may not be null!");

        this.plugin = plugin;
        this.taskExecutor = taskExecutor;
        this.logging = logging;

        this.initializePluginDetails(plugin);

        this.fileDownloader = new DefaultFileDownloader(this, this.pluginName);

        AutumnTranslationProperty.scan(DefaultAutumn.class, "Autumn");
        AutumnTranslationProperty.scan(plugin.getClass(), this.pluginName);
    }

    protected abstract void initializePluginDetails(Object pluginObject);

    protected abstract void initializePlugin(Object pluginObject);

    protected abstract void loadPlugin(File pluginFile);

    protected abstract boolean isLoadedPlugin(String plugin);

    protected abstract DefaultAutumnInjector getAutumnInjector(Object pluginObject);

    @Override
    public void defaultSetupStates() {
        this.setupManager.mainSetupState(MainSetupStates.CONSOLE_LANGUAGE);
    }

    @Override
    public void databaseSetupStates() {
        for(SetupState setupState : MainSetupStates.values()) {
            this.setupManager.mainSetupState(setupState);
        }
    }

    @Override
    public void extraSetupStates(SetupState... setupStates) {
        for(SetupState setupState : setupStates) {
            this.setupManager.extraSetupState(setupState);
        }
    }

    @Override
    public void initialize(AutumnCallback<AutumnInjector> initializationCallback) {
        this.initializationCallback = initializationCallback;

        this.downloadDependencies(() -> {
            this.zipFileDownloader = new DefaultZipFileDownloader(this, this.pluginName);
            this.injector = this.getAutumnInjector(this.plugin);

            this.injector.classLoaders(this.autumnClassLoader);
            this.injector.classLoaders(this.pluginClassLoader);

            this.injector.registerModule(AutumnTaskExecutor.class, this.taskExecutor);
            this.injector.registerModule(AutumnLogging.class, this.logging);
            this.injector.registerModule(DefaultAutumnInjector.class, this.injector);
            this.injector.registerModule(AutumnLanguage.class, this.defaultLanguage);
            this.injector.registerModule(DefaultSetupManager.class, this.setupManager);

            if(this.database != null) {
                this.injector.registerModule(Database.class, this.database);
            }

            this.injector.annotation(AutumnSetupListener.class);
            this.injector.scan();

            if(this.configurationFile != null) {
                AutumnConfiguration configuration = new DefaultAutumnConfiguration(this.configurationFile);
                Document document = configuration.read();

                if(document != null && document.keys() != null && !document.keys().isEmpty()) {
                    this.setupManager.complete(false);
                    return;
                }
            }

            this.setupManager.begin();
        });
    }

    @Override
    public void enableDatabase(DataSourceProperty... dataSourceProperties) {
        this.database = new DefaultDatabase(this);

        for(DataSourceProperty dataSourceProperty : dataSourceProperties) {
            this.database.generateDataSourceProperty(dataSourceProperty);
        }

        this.database.generateDataSourceProperty(AutumnLanguage.DataSource.PROPERTY);
    }

    @Override
    public boolean optionalCommands() {
        return this.optionalCommands;
    }

    @Override
    public void optionalCommands(boolean optionalCommands) {
        this.optionalCommands = optionalCommands;
    }

    @Override
    public boolean optionalListeners() {
        return this.optionalListeners;
    }

    @Override
    public void optionalListeners(boolean optionalListeners) {
        this.optionalListeners = optionalListeners;
    }

    public void postSetupInitialization() {
        this.initializePlugin(this.plugin);

        if(this.database != null && this.databaseType != null) {
            this.database.setType(this.databaseType);

            this.database.connect(connected -> {
                AutumnTranslation translation = new DefaultAutumnTranslation(this.database.getDatabaseType().readyTranslationProperty());
                translation.replacement("plugin", this.pluginName);

                this.logging.info(translation.get(this.getDefaultLanguage()));
            });
        }

        AutumnTranslation translation = new DefaultAutumnTranslation("autumn.plugin.initialized");
        translation.replacement("plugin", this.pluginName);
        translation.replacement("version", this.pluginVersion);

        this.logging.info(translation.get(this.getDefaultLanguage()));

        this.initializationCallback.done(this.injector);

        this.injector.annotation(AutumnCommand.class);
        this.injector.annotation(AutumnListener.class);
        this.injector.scan();
    }

    public void setDefaultLanguage(AutumnLanguage defaultLanguage) {
        if(defaultLanguage != null) this.defaultLanguage = defaultLanguage;
    }

    public void setDatabaseType(Database.Type databaseType) {
        if(databaseType != null) this.databaseType = databaseType;
    }

    protected void setPluginFolder(File pluginFolder) {
        if(!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        this.pluginFolder = pluginFolder;
        this.configurationFile = new File(this.pluginFolder, "config.json");
    }

    private void downloadDependencies(AutumnEmptyCallback emptyCallback) {
        AutumnRepository autumnRepository = new DefaultAutumnRepository();
        autumnRepository.setName("dementisimus.dev");
        autumnRepository.setURL("https://repo.dementisimus.dev/release/");

        AutumnDependency autumnDependency = new DefaultAutumnDependency();

        autumnDependency.setRepository(autumnRepository);
        autumnDependency.setGroupId("dev.dementisimus.autumn");
        autumnDependency.setArtifactId("autumn-dependencies");
        autumnDependency.setVersion("1.0.0");

        File dependenciesPluginFile = new File("plugins/" + autumnDependency.getFileName());
        SysOut.debug("1");
        if(!this.isLoadedPlugin("Autumn-Dependencies")) {
            SysOut.debug("2");
            if(!dependenciesPluginFile.exists()) {
                SysOut.debug("3");
                this.fileDownloader.setDownloadTo(new File("plugins/"));
                this.fileDownloader.download(autumnDependency, file -> {
                    SysOut.debug("4");
                    this.loadPlugin(file);
                    emptyCallback.done();
                });
                return;
            }else {
                SysOut.debug("5");
                this.loadPlugin(dependenciesPluginFile);
            }
        }
        emptyCallback.done();
    }
}
