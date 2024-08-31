package me.wyzebb.maprepair;

import me.wyzebb.maprepair.commands.CommandManager;
import me.wyzebb.maprepair.events.BreakBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MapRepair extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin started!");

        // Config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        // Register join and leave events
        getServer().getPluginManager().registerEvents(new BreakBlockEvent(), this);

        // Register commands and tab completer
        Objects.requireNonNull(getCommand("maprepair")).setExecutor(new CommandManager(this));
        Objects.requireNonNull(getCommand("maprepair")).setTabCompleter(new CommandManager(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
