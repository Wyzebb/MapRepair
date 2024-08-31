package me.wyzebb.maprepair.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockEvent implements Listener {
    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        e.getPlayer().sendMessage("You broke " + e.getBlock());
    }
}
