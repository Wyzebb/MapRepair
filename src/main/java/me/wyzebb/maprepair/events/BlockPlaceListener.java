package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final BlockDataHandler blockDataHandler;
    private final MapRepair plugin;
    private final LanguageManager languageManager;

    public BlockPlaceListener(MapRepair plugin) {
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        final Location loc = e.getBlock().getLocation();

        if (blockDataHandler != null) {
            // Get the placed block's data
            BlockData blockData = e.getBlockPlaced().getBlockData();

            if (blockData instanceof Bed bedData) {
                // Determine the other half of the bed
                Block otherHalf;
                if (bedData.getPart() == Bed.Part.HEAD) {
                    // It's the head; get the foot half
                    otherHalf = e.getBlockPlaced().getRelative(bedData.getFacing().getOppositeFace());
                } else {
                    // It's the foot; get the head half
                    otherHalf = e.getBlockPlaced().getRelative(bedData.getFacing());
                }

                // Put air where the head of the bed was
                BlockData airBlockData = Material.AIR.createBlockData();
                blockDataHandler.saveBlockData(otherHalf.getLocation(), airBlockData);
            }

            // Save the data of the block that was there before
            blockDataHandler.saveBlockData(loc, e.getBlockReplacedState().getBlockData());
        } else {
            plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
        }
    }
}
