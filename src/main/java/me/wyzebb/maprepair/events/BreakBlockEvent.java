package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockEvent implements Listener {

    private final BlockDataHandler blockDataHandler;
    private final MapRepair plugin;
    private final LanguageManager languageManager;

    public BreakBlockEvent(MapRepair plugin) {
        this.plugin = plugin;
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.languageManager = plugin.getLanguageManager();
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        final Location loc = e.getBlock().getLocation();
        final String blockMaterial = e.getBlock().getType().toString();

        if (blockDataHandler != null) {
            blockDataHandler.saveBlockData(loc, blockMaterial);
        } else {
            plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
        }
    }
}
