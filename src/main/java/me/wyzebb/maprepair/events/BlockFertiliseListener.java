package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

import java.util.List;

public class BlockFertiliseListener implements Listener {

    private final BlockDataHandler blockDataHandler;
    private final MapRepair plugin;
    private final LanguageManager languageManager;

    public BlockFertiliseListener(MapRepair plugin) {
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @EventHandler
    private void onBlockFertilise(BlockFertilizeEvent e) {
        List<BlockState> blocks = e.getBlocks();

        for (BlockState block : blocks) {
            final Location loc = block.getLocation();

            if (blockDataHandler != null) {
                blockDataHandler.saveBlockData(loc, block.getBlock().getBlockData());
            } else {
                plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
            }
        }
    }
}
