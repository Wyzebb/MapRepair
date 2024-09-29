package me.wyzebb.maprepair;

import me.wyzebb.maprepair.commands.CommandManager;
import me.wyzebb.maprepair.events.*;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public final class MapRepair extends JavaPlugin {

    private BlockDataHandler blockDataHandler;
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        getLogger().info("Plugin started!");

        reloadConfig();

        blockDataHandler = new BlockDataHandler(this);
        blockDataHandler.setupServerStartData();

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBurnListener(this), this);
        getServer().getPluginManager().registerEvents(new LeavesDecayListener(this), this);
        getServer().getPluginManager().registerEvents(new ExplosionListener(this), this);
        getServer().getPluginManager().registerEvents(new FadeBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockFertiliseListener(this), this);
        getServer().getPluginManager().registerEvents(new LavacastListener(this), this);
        getServer().getPluginManager().registerEvents(new FluidListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockGrowListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockIgniteListener(this), this);
        getServer().getPluginManager().registerEvents(new TNTPrimeListener(this), this);
        getServer().getPluginManager().registerEvents(new FireSpreadListener(this), this);
        getServer().getPluginManager().registerEvents(new BucketListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPhysicsListener(this), this);

        CommandManager commandManager = new CommandManager(this);
        Objects.requireNonNull(getCommand("maprepair")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("maprepair")).setTabCompleter(commandManager);
    }

    @Override
    public void onDisable() {
        blockDataHandler.saveAllData(getDataFolder());
    }

    public BlockDataHandler getBlockDataHandler() {
        return blockDataHandler;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        saveDefaultConfig();
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        languageManager = new LanguageManager(this);
    }
}
