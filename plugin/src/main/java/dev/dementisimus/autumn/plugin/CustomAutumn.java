/*
 | Copyright 2022 dementisimus,
 | licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License. 
 |
 | To view a copy of this license,
 | visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 */

package dev.dementisimus.autumn.plugin;

import com.google.common.base.Preconditions;
import dev.dementisimus.autumn.api.Autumn;
import dev.dementisimus.autumn.api.callback.EmptyCallback;
import dev.dementisimus.autumn.api.command.AutumnCommand;
import dev.dementisimus.autumn.api.configuration.AutumnConfiguration;
import dev.dementisimus.autumn.api.i18n.AutumnLanguage;
import dev.dementisimus.autumn.api.setup.SetupManager;
import dev.dementisimus.autumn.api.setup.state.SetupState;
import dev.dementisimus.autumn.api.storage.Storage;
import dev.dementisimus.autumn.api.storage.property.source.StorageSourceProperty;
import dev.dementisimus.autumn.plugin.command.CommandRegistry;
import dev.dementisimus.autumn.plugin.command.ConfigureCommand;
import dev.dementisimus.autumn.plugin.command.CustomAutumnCommand;
import dev.dementisimus.autumn.plugin.command.TestCommand;
import dev.dementisimus.autumn.plugin.configuration.CustomAutumnConfiguration;
import dev.dementisimus.autumn.plugin.executor.CustomAutumnTaskExecutor;
import dev.dementisimus.autumn.plugin.factory.item.interaction.listener.ItemFactoryClickInteractionListener;
import dev.dementisimus.autumn.plugin.factory.item.interaction.listener.ItemFactoryDropInteractionListener;
import dev.dementisimus.autumn.plugin.factory.item.interaction.listener.ItemFactoryInteractionListener;
import dev.dementisimus.autumn.plugin.file.CustomFileDownloader;
import dev.dementisimus.autumn.plugin.file.CustomZipFileDownloader;
import dev.dementisimus.autumn.plugin.i18n.CustomTranslation;
import dev.dementisimus.autumn.plugin.i18n.property.TranslationProperty;
import dev.dementisimus.autumn.plugin.language.CustomLanguageSelection;
import dev.dementisimus.autumn.plugin.language.command.LanguageCommand;
import dev.dementisimus.autumn.plugin.listener.AsyncPlayerChatListener;
import dev.dementisimus.autumn.plugin.listener.InventoryClickListener;
import dev.dementisimus.autumn.plugin.listener.PlayerLocaleChangeListener;
import dev.dementisimus.autumn.plugin.listener.ServerCommandListener;
import dev.dementisimus.autumn.plugin.log.CustomAutumnLogger;
import dev.dementisimus.autumn.plugin.npc.pool.CustomAutumnNPCPool;
import dev.dementisimus.autumn.plugin.setup.CustomSetupManager;
import dev.dementisimus.autumn.plugin.setup.PluginSetupManager;
import dev.dementisimus.autumn.plugin.setup.state.MainSetupStates;
import dev.dementisimus.autumn.plugin.setup.value.SetupValueManager;
import dev.dementisimus.autumn.plugin.storage.CustomStorage;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomAutumn implements Autumn {

    private static final List<SetupManager> SETUP_QUEUE = new ArrayList<>();

    private static boolean activeSetup = false;

    private final Plugin plugin;
    private final CustomAutumnTaskExecutor taskExecutor;
    private final String pluginName;
    private final String pluginVersion;
    private final CustomAutumnLogger logger;
    private final CustomSetupManager setupManager;
    private final String serverVersion;
    private final File dataFolder;
    private final File configurationFile;
    private final CustomFileDownloader fileDownloader;

    private SetupValueManager setupValueManager;
    private CustomZipFileDownloader zipFileDownloader;
    private Storage.Type storageType;
    private CustomStorage storage;
    private AutumnLanguage defaultLanguage = AutumnLanguage.ENGLISH;

    private EmptyCallback initializationCallback;

    private CommandRegistry commandRegistry;
    private CustomLanguageSelection languageSelection;
    private CustomAutumnNPCPool npcPool;

    public CustomAutumn(Plugin plugin, String pluginPrefix) {
        Preconditions.checkNotNull(plugin, "Plugin may not be null!");
        Preconditions.checkNotNull(pluginPrefix, "PluginPrefix may not be null!");

        this.plugin = plugin;
        this.taskExecutor = new CustomAutumnTaskExecutor(plugin);

        this.pluginName = plugin.getName();
        this.pluginVersion = plugin.getPluginMeta().getVersion();

        this.logger = new CustomAutumnLogger(plugin.getLogger(), this.defaultLanguage, this.pluginName, this.pluginVersion, pluginPrefix);

        this.setupManager = new PluginSetupManager(this);

        this.serverVersion = Bukkit.getVersion();
        this.dataFolder = plugin.getDataFolder();

        if (!this.dataFolder.exists()) {
            this.dataFolder.mkdirs();
        }

        this.configurationFile = new File(this.dataFolder, "config.json");
        this.fileDownloader = new CustomFileDownloader(this, this.pluginName);

        TranslationProperty.scan(CustomAutumn.class, "Autumn");
        TranslationProperty.scan(plugin.getClass(), this.pluginName);

        SetupManager setupManager = this.setupManager;
        if (!setupManager.isCompleted()) {
            ServerCommandListener serverCommandListener = new ServerCommandListener(this, this.setupManager);

            this.setupValueManager = serverCommandListener;

            Bukkit.getPluginManager().registerEvents(serverCommandListener, plugin);
        }
    }

    public static void toQueue(SetupManager setupManager) {
        if (SETUP_QUEUE.contains(setupManager)) return;

        SETUP_QUEUE.add(setupManager);

        beginNextSetupInQueue();
    }

    private static void beginNextSetupInQueue() {
        if (!activeSetup && !SETUP_QUEUE.isEmpty()) {
            SetupManager nextSetup = SETUP_QUEUE.get(0);

            activeSetup = true;
            nextSetup.begin();

            SETUP_QUEUE.remove(nextSetup);
        }
    }

    @Override
    public void defaultSetupStates() {
        this.setupManager.mainSetupState(MainSetupStates.CONSOLE_LANGUAGE);
    }

    @Override
    public void storageSetupStates() {
        for (SetupState setupState : MainSetupStates.values()) {
            this.setupManager.mainSetupState(setupState);
        }
    }

    @Override
    public void extraSetupStates(SetupState... setupStates) {
        for (SetupState setupState : setupStates) {
            this.extraSetupState(setupState);
        }
    }

    @Override
    public void extraSetupState(SetupState setupState) {
        this.setupManager.extraSetupState(setupState);
    }

    @Override
    public void initialize(EmptyCallback initializationCallback) {
        this.initializationCallback = initializationCallback;

        this.zipFileDownloader = new CustomZipFileDownloader(this, this.pluginName);

        if (this.configurationFile != null) {
            AutumnConfiguration configuration = new CustomAutumnConfiguration(this.configurationFile);
            Document document = configuration.read();

            if (!document.isEmpty()) {
                this.setupManager.complete(false);
                return;
            }
        }

        toQueue(this.setupManager);
    }

    @Override
    public void useStorage(StorageSourceProperty... storageSourceProperties) {
        this.storage = new CustomStorage(this);

        for (StorageSourceProperty storageSourceProperty : storageSourceProperties) {
            this.storage.generateSourceProperty(storageSourceProperty);
        }

        this.storage.generateSourceProperty(AutumnLanguage.StorageSource.PROPERTY);
    }

    @Override
    public void registerCommand(AutumnCommand command) {
        this.commandRegistry.registerCommand((CustomAutumnCommand) command);
    }

    @Override
    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this.plugin);
    }

    public void postSetupInitialization() {
        this.commandRegistry = new CommandRegistry(this.plugin, this);
        this.languageSelection = new CustomLanguageSelection(this);
        this.npcPool = new CustomAutumnNPCPool(this, this.plugin);

        if (this.storage != null && this.storageType != null) {
            this.storage.setType(this.storageType);

            this.storage.connect(connected -> {
                this.logger.info(new CustomTranslation(this.storage.getStorageType().getReadyTranslationProperty()));
            });
        }

        this.logger.info(new CustomTranslation("autumn.plugin.initialized"));

        this.registerCommand(new LanguageCommand(this));
        this.registerCommand(new ConfigureCommand(this));
        this.registerCommand(new TestCommand(this));

        this.registerListener(new PlayerLocaleChangeListener(this.storage));
        this.registerListener(new AsyncPlayerChatListener());
        this.registerListener(new InventoryClickListener());
        this.registerListener(new ItemFactoryClickInteractionListener());
        this.registerListener(new ItemFactoryDropInteractionListener());
        this.registerListener(new ItemFactoryInteractionListener());

        this.initializationCallback.done();

        activeSetup = false;
        beginNextSetupInQueue();
    }

    public void setDefaultLanguage(AutumnLanguage defaultLanguage) {
        if (defaultLanguage != null) this.defaultLanguage = defaultLanguage;
    }

    public void setStorageType(Storage.Type storageType) {
        if (storageType != null) this.storageType = storageType;
    }
}
