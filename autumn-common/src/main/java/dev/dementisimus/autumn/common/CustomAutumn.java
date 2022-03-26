/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.common;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.common.api.Autumn;
import dev.dementisimus.autumn.common.api.callback.AutumnEmptyCallback;
import dev.dementisimus.autumn.common.api.callback.AutumnSingleCallback;
import dev.dementisimus.autumn.common.api.configuration.AutumnConfiguration;
import dev.dementisimus.autumn.common.api.dependency.AutumnDependency;
import dev.dementisimus.autumn.common.api.dependency.AutumnRepository;
import dev.dementisimus.autumn.common.api.executor.AutumnTaskExecutor;
import dev.dementisimus.autumn.common.api.file.AutumnFileDownloader;
import dev.dementisimus.autumn.common.api.file.AutumnZipFileDownloader;
import dev.dementisimus.autumn.common.api.i18n.AutumnLanguage;
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
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomAutumn implements Autumn {

    private static final List<SetupManager> SETUP_QUEUE = new ArrayList<>();

    private static boolean activeSetup = false;

    private final Object plugin;
    private final AutumnTaskExecutor taskExecutor;
    private final AutumnLogging logging;
    private final CustomFileDownloader fileDownloader;
    @Getter(AccessLevel.PROTECTED) private CustomAutumnInjector injector;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private CustomSetupManager setupManager;
    private CustomZipFileDownloader zipFileDownloader;
    private Storage.Type storageType;
    private CustomStorage storage;
    private AutumnLanguage defaultLanguage;

    @Setter(AccessLevel.PROTECTED) private ClassLoader autumnClassLoader;
    @Setter(AccessLevel.PROTECTED) private ClassLoader pluginClassLoader;
    @Setter(AccessLevel.PROTECTED) private SetupValueManager setupValueManager;

    @Setter(AccessLevel.PROTECTED) private String pluginName;
    @Setter(AccessLevel.PROTECTED) private String pluginVersion;
    @Setter(AccessLevel.PROTECTED) private ServerType servertype;
    @Setter(AccessLevel.PROTECTED) private String serverVersion;

    private File pluginFolder;
    private File configurationFile;
    private AutumnSingleCallback<AutumnInjector> initializationCallback;
    private boolean optionalCommands;
    private boolean optionalListeners;
    private boolean skipInjection;

    public CustomAutumn(Object plugin, String pluginPrefix, AutumnTaskExecutor taskExecutor, AutumnLogging logging) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null!");
        Preconditions.checkNotNull(pluginPrefix, "PluginPrefix may not be null!");
        Preconditions.checkNotNull(taskExecutor, "TaskExecutor may not be null!");
        Preconditions.checkNotNull(logging, "Logging may not be null!");

        this.plugin = plugin;
        this.taskExecutor = taskExecutor;
        this.logging = logging;

        this.setDefaultLanguage(AutumnLanguage.ENGLISH);

        this.logging.pluginPrefix(pluginPrefix);

        this.initializePluginDetails(plugin);

        this.logging.pluginName(this.pluginName);
        this.logging.pluginVersion(this.pluginVersion);

        this.fileDownloader = new CustomFileDownloader(this, this.pluginName);

        AutumnTranslationProperty.scan(CustomAutumn.class, "Autumn");
        AutumnTranslationProperty.scan(plugin.getClass(), this.pluginName);
    }

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
            this.extraSetupState(setupState);
        }
    }

    @Override
    public void extraSetupState(@NotNull SetupState setupState) {
        this.setupManager.extraSetupState(setupState);
    }

    @Override
    public void initialize(@NotNull AutumnSingleCallback<@NotNull AutumnInjector> initializationCallback) {
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

                if(!document.isEmpty()) {
                    this.setupManager.complete(false);
                    return;
                }
            }

            toQueue(this.setupManager);
        });
    }

    @Override
    public void useStorage(@NotNull StorageSourceProperty... storageSourceProperties) {
        this.storage = new CustomStorage(this);

        for(StorageSourceProperty storageSourceProperty : storageSourceProperties) {
            this.storage.generateSourceProperty(storageSourceProperty);
        }

        this.storage.generateSourceProperty(AutumnLanguage.StorageSource.PROPERTY);
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
    public boolean skipInjection() {
        return this.skipInjection;
    }

    @Override
    public void skipInjection(boolean skipInjection) {
        this.skipInjection = skipInjection;
    }

    @Override
    public AutumnTaskExecutor taskExecutor() {
        return this.taskExecutor;
    }

    @Override
    public AutumnLogging logging() {
        return this.logging;
    }

    @Override
    public AutumnInjector injector() {
        return this.injector;
    }

    @Override
    public AutumnLanguage defaultLanguage() {
        return this.defaultLanguage;
    }

    @Override
    public SetupManager setupManager() {
        return this.setupManager;
    }

    @Override
    public Storage storage() {
        return this.storage;
    }

    @Override
    public AutumnFileDownloader fileDownloader() {
        return this.fileDownloader;
    }

    @Override
    public AutumnZipFileDownloader zipfileDownloader() {
        return this.zipFileDownloader;
    }

    @Override
    public String pluginName() {
        return this.pluginName;
    }

    @Override
    public File configurationFile() {
        return this.configurationFile;
    }

    @Override
    public File pluginFolder() {
        return this.pluginFolder;
    }

    protected abstract void initializePluginDetails(Object pluginObject);

    protected abstract void initializePlugin(Object pluginObject);

    protected abstract void loadPlugin(File pluginFile);

    protected abstract boolean isLoadedPlugin(String plugin);

    protected abstract CustomAutumnInjector getAutumnInjector(Object pluginObject);

    public void postSetupInitialization() {
        this.initializePlugin(this.plugin);

        if(this.storage != null && this.storageType != null) {
            this.storage.setType(this.storageType);

            this.storage.connect(connected -> {
                this.logging.info(new CustomAutumnTranslation(this.storage.getStorageType().readyTranslationProperty()));
            });
        }

        this.logging.info(new CustomAutumnTranslation("autumn.plugin.initialized"));

        this.initializationCallback.done(this.injector);

        this.injector.annotation(AutumnCommand.class);
        this.injector.annotation(AutumnListener.class);

        if(!this.skipInjection) {
            this.injector.scan();
        }

        activeSetup = false;
        beginNextSetupInQueue();
    }

    public void setDefaultLanguage(AutumnLanguage defaultLanguage) {
        if(defaultLanguage != null) {
            this.defaultLanguage = defaultLanguage;
            this.logging.consoleLanguage(defaultLanguage);
        }
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
        autumnDependency.groupId("dev.dementisimus");
        autumnDependency.artifactId("autumn-dependencies");
        autumnDependency.version("1.1.0");

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

    public static void toQueue(SetupManager setupManager) {
        if(SETUP_QUEUE.contains(setupManager)) return;

        SETUP_QUEUE.add(setupManager);

        beginNextSetupInQueue();
    }

    private static void beginNextSetupInQueue() {
        if(!activeSetup && !SETUP_QUEUE.isEmpty()) {
            SetupManager nextSetup = SETUP_QUEUE.get(0);

            activeSetup = true;
            nextSetup.begin();

            SETUP_QUEUE.remove(nextSetup);
        }
    }
}
