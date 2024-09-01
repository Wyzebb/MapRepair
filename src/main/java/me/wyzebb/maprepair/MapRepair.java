package me.wyzebb.maprepair;

import me.wyzebb.maprepair.commands.CommandManager;
import me.wyzebb.maprepair.events.BreakBlockEvent;
import me.wyzebb.maprepair.events.PlaceBlockEvent;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MapRepair extends JavaPlugin {

    private BlockDataHandler blockDataHandler;
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        getLogger().info("Plugin started!");

        // Config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        languageManager = new LanguageManager(this);

        // Initialize BlockDataHandler
        blockDataHandler = new BlockDataHandler(this);
        blockDataHandler.setupServerStartData();

        // Register join and leave events
        getServer().getPluginManager().registerEvents(new BreakBlockEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlaceBlockEvent(this), this);
        CommandManager commandManager = new CommandManager(this);
        Objects.requireNonNull(getCommand("maprepair")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("maprepair")).setTabCompleter(commandManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        blockDataHandler.serverShut();
    }

    public BlockDataHandler getBlockDataHandler() {
        return blockDataHandler;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}
