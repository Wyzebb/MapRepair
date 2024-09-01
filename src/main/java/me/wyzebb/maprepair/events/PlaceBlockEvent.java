package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBlockEvent implements Listener {

    private final BlockDataHandler blockDataHandler;
    private final MapRepair plugin;

    public PlaceBlockEvent(MapRepair plugin) {
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.plugin = plugin;
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        final Location loc = e.getBlock().getLocation();
        final String blockMaterial = e.getBlockReplacedState().getType().toString();

        if (blockDataHandler != null) {
            blockDataHandler.saveBlockData(loc, blockMaterial);
        } else {
            plugin.getLogger().warning("Block data handler has not been initialised!");
        }
    }
}
