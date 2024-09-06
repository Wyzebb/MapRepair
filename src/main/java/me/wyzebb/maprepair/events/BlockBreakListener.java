package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final BlockDataHandler blockDataHandler;
    private final MapRepair plugin;
    private final LanguageManager languageManager;

    public BlockBreakListener(MapRepair plugin) {
        this.plugin = plugin;
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.languageManager = plugin.getLanguageManager();
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        final Location loc = e.getBlock().getLocation();

        if (blockDataHandler != null) {
            blockDataHandler.saveBlockData(loc, e.getBlock().getBlockData());
        } else {
            plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
        }
    }
}
