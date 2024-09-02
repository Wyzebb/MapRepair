package me.wyzebb.maprepair.commands.subcommands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.ProcessConfigMessagesUtility;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetmapCommand extends SubCommand {

    private final MapRepair plugin;
    private final BlockDataHandler blockDataHandler;

    public SetmapCommand(MapRepair plugin) {
        this.plugin = plugin;
        this.blockDataHandler = plugin.getBlockDataHandler();
    }

    @Override
    public String getName() {
        return "setmap";
    }

    @Override
    public String getDescription() {
        return "Sets the current world as your desired map. This is what it will repair to (execute this command after editing your map)";
    }

    @Override
    public String getSyntax() {
        return "/maprepair setmap";
    }

    @Override
    public void performCommand(CommandSender commandSender, String[] args) {
        if (blockDataHandler == null) {
            commandSender.sendMessage("BlockDataHandler is not initialized!");
            return;
        }
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("This command can only be run by a player!");
            return;
        }

        String playerWorldName = player.getWorld().getName();

        ProcessConfigMessagesUtility.processMessage("messages.restoring", commandSender);

        // After the loop, clear all the data for the world
        blockDataHandler.clearWorldData(playerWorldName);

        ProcessConfigMessagesUtility.processMessage("messages.restored", commandSender);
    }
}
