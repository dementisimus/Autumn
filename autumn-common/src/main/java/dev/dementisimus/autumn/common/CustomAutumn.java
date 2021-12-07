/*
 | Copyright 2021 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common;

import com.github.derrop.documents.Document;
import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.common.api.Autumn;
import dev.dementisimus.autumn.common.api.callback.AutumnCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnEmptyCallback;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
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
import dev.dementisimus.autumn.common.api.setup.SetupManager;
import dev.dementisimus.autumn.common.api.setup.state.SetupState;
import dev.dementisimus.autumn.common.api.storage.Storage;
import dev.dementisimus.autumn.common.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.common.configuration.CustomAutumnConfiguration;
import dev.dementisimus.autumn.common.dependency.CustomAutumnDependency;
import dev.dementisimus.autumn.common.dependency.CustomAutumnRepository;
import dev.dementisimus.autumn.common.file.CustomFileDownloader;
import dev.dementisimus.autumn.common.file.CustomZipFileDownloader;
import dev.dementisimus.autumn.common.i18n.CustomAutumnTranslation;
import dev.dementisimus.autumn.common.i18n.property.AutumnTranslationProperty;
import dev.dementisimus.autumn.common.injection.CustomAutumnInjector;
import dev.dementisimus.autumn.common.setup.CustomSetupManager;
import dev.dementisimus.autumn.common.setup.state.MainSetupStates;
import dev.dementisimus.autumn.common.setup.value.SetupValueManager;
import dev.dementisimus.autumn.common.storage.CustomStorage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class CustomAutumn implements Autumn {

    @Getter(AccessLevel.PROTECTED) private final Object plugin;
    @Getter private final AutumnTaskExecutor taskExecutor;
    @Getter private final AutumnLogging logging;
    @Getter private final CustomFileDownloader fileDownloader;

    @Getter private CustomAutumnInjector injector;
    @Getter @Setter(AccessLevel.PROTECTED) private CustomSetupManager setupManager;
    @Getter private CustomZipFileDownloader zipFileDownloader;

    @Getter private Storage.Type storageType;
    @Getter private CustomStorage storage;
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

    public CustomAutumn(Object plugin, AutumnTaskExecutor taskExecutor, AutumnLogging logging) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null!");
        Preconditions.checkNotNull(taskExecutor, "TaskExecutor may not be null!");
        Preconditions.checkNotNull(logging, "Logging may not be null!");

        this.plugin = plugin;
        this.taskExecutor = taskExecutor;
        this.logging = logging;

        this.initializePluginDetails(plugin);

        this.fileDownloader = new CustomFileDownloader(this, this.pluginName);

        AutumnTranslationProperty.scan(CustomAutumn.class, "Autumn");
        AutumnTranslationProperty.scan(plugin.getClass(), this.pluginName);
    }

    protected abstract void initializePluginDetails(Object pluginObject);

    protected abstract void initializePlugin(Object pluginObject);

    protected abstract void loadPlugin(File pluginFile);

    protected abstract boolean isLoadedPlugin(String plugin);

    protected abstract CustomAutumnInjector getAutumnInjector(Object pluginObject);

    @Override
    public void defaultSetupStates() {
        this.setupManager.mainSetupState(MainSetupStates.CONSOLE_LANGUAGE);
    }

    @Override
    public void storageSetupStates() {
        for(SetupState setupState : MainSetupStates.values()) {
            this.setupManager.mainSetupState(setupState);
        }
    }

    @Override
    public void extraSetupStates(@NotNull SetupState... setupStates) {
        for(SetupState setupState : setupStates) {
            this.setupManager.extraSetupState(setupState);
        }
    }

    @Override
    public void initialize(@NotNull AutumnCallback<@NotNull AutumnInjector> initializationCallback) {
        this.initializationCallback = initializationCallback;

        this.downloadDependencies(() -> {
            this.zipFileDownloader = new CustomZipFileDownloader(this, this.pluginName);
            this.injector = this.getAutumnInjector(this.plugin);

            this.injector.classLoaders(this.autumnClassLoader);
            this.injector.classLoaders(this.pluginClassLoader);

            this.injector.registerModule(AutumnTaskExecutor.class, this.taskExecutor);
            this.injector.registerModule(AutumnLogging.class, this.logging);
            this.injector.registerModule(CustomAutumnInjector.class, this.injector);
            this.injector.registerModule(AutumnLanguage.class, this.defaultLanguage);
            this.injector.registerModule(CustomSetupManager.class, this.setupManager);
            this.injector.registerModule(Storage.class, this.storage);

            this.injector.annotation(AutumnSetupListener.class);
            this.injector.scan();

            if(this.configurationFile != null) {
                AutumnConfiguration configuration = new CustomAutumnConfiguration(this.configurationFile);
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
    public void useStorage(@NotNull StorageSourceProperty... storageSourceProperties) {
        this.storage = new CustomStorage(this);

        for(StorageSourceProperty storageSourceProperty : storageSourceProperties) {
            this.storage.generateStorageSourceProperty(storageSourceProperty);
        }

        this.storage.generateStorageSourceProperty(AutumnLanguage.StorageSource.PROPERTY);
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

    @Override
    public @NotNull AutumnTaskExecutor taskExecutor() {
        return this.taskExecutor;
    }

    @Override
    public @NotNull AutumnLogging logging() {
        return this.logging;
    }

    @Override
    public @NotNull AutumnInjector injector() {
        return this.injector;
    }

    @Override
    public @NotNull AutumnLanguage defaultLanguage() {
        return this.defaultLanguage;
    }

    @Override
    public @NotNull SetupManager setupManager() {
        return this.setupManager;
    }

    @Override
    public @Nullable Storage storage() {
        return this.storage;
    }

    public void postSetupInitialization() {
        this.initializePlugin(this.plugin);

        if(this.storage != null && this.storageType != null) {
            this.storage.setType(this.storageType);

            this.storage.connect(connected -> {
                AutumnTranslation translation = new CustomAutumnTranslation(this.storage.getStorageType().readyTranslationProperty());
                translation.replacement("plugin", this.pluginName);

                this.logging.info(translation.get(this.getDefaultLanguage()));
            });
        }

        AutumnTranslation translation = new CustomAutumnTranslation("autumn.plugin.initialized");
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

    public void setStorageType(Storage.Type storageType) {
        if(storageType != null) this.storageType = storageType;
    }

    protected void setPluginFolder(File pluginFolder) {
        if(!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        this.pluginFolder = pluginFolder;
        this.configurationFile = new File(this.pluginFolder, "config.json");
    }

    private void downloadDependencies(AutumnEmptyCallback emptyCallback) {
        AutumnRepository autumnRepository = new CustomAutumnRepository();
        autumnRepository.name("dementisimus.dev");
        autumnRepository.url("https://repo.dementisimus.dev/release/");

        AutumnDependency autumnDependency = new CustomAutumnDependency();

        autumnDependency.repository(autumnRepository);
        autumnDependency.groupId("dev.dementisimus.autumn");
        autumnDependency.artifactId("autumn-dependencies");
        autumnDependency.version("1.0.0");

        File dependenciesPluginFile = new File("plugins/" + autumnDependency.fileName());
        if(!this.isLoadedPlugin("Autumn-Dependencies")) {
            if(!dependenciesPluginFile.exists()) {
                this.fileDownloader.downloadTo(new File("plugins/"));
                this.fileDownloader.download(autumnDependency, file -> {
                    this.loadPlugin(file);
                    emptyCallback.done();
                });
                return;
            }else {
                this.loadPlugin(dependenciesPluginFile);
            }
        }
        emptyCallback.done();
    }
}
