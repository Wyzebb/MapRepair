package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class FluidListener implements Listener {

    private final BlockDataHandler blockDataHandler;
    private final MapRepair plugin;
    private final LanguageManager languageManager;

    public FluidListener(MapRepair plugin) {
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @EventHandler
    private void onLiquidUpdate(BlockFromToEvent e) {
        Block fromBlock = e.getBlock();
        Block toBlock = e.getToBlock();

        if (fromBlock.getType() == Material.WATER || fromBlock.getType() == Material.LAVA) {
            Location toLoc = toBlock.getLocation();

            if (blockDataHandler != null) {
                blockDataHandler.saveBlockData(toLoc, toBlock.getBlockData());
            } else {
                plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
            }
        }
    }
}
