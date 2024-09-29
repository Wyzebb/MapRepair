package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bed;
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
                // Get the block's data
                BlockData blockData = e.getBlock().getBlockData();

                if (blockData instanceof Bed bedData) {
                    // Check if this is the head or the foot of the bed
                    if (bedData.getPart() == Bed.Part.HEAD) {
                        Block otherHalf = e.getBlock().getRelative(bedData.getFacing().getOppositeFace());
                        blockDataHandler.saveBlockData(otherHalf.getLocation(), otherHalf.getBlockData());
                    } else {
                        Block otherHalf = e.getBlock().getRelative(bedData.getFacing());
                        blockDataHandler.saveBlockData(otherHalf.getLocation(), otherHalf.getBlockData());
                    }
                }
            blockDataHandler.saveBlockData(loc, e.getBlock().getBlockData());
        } else {
            plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
        }
    }
}
