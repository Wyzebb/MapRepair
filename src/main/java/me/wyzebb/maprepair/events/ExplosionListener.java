package me.wyzebb.maprepair.events;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class ExplosionListener implements Listener {

    private final BlockDataHandler blockDataHandler;
    private final MapRepair plugin;
    private final LanguageManager languageManager;

    public ExplosionListener(MapRepair plugin) {
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();
        for (Block block : blocks) {
            final Location loc = block.getLocation();

            if (blockDataHandler != null) {
                blockDataHandler.saveBlockData(loc, block.getBlockData());
            } else {
                plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
            }
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        List<Block> blocks = event.blockList();
        for (Block block : blocks) {
            final Location loc = block.getLocation();

            if (blockDataHandler != null) {
                blockDataHandler.saveBlockData(loc, block.getBlockData());
            } else {
                plugin.getLogger().warning(languageManager.getLanguageFile().getString("error"));
            }
        }
    }
}
