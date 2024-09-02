package me.wyzebb.maprepair.commands.subcommands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.ProcessConfigMessagesUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class RepairCommand extends SubCommand {

    private final MapRepair plugin;
    private final BlockDataHandler blockDataHandler;

    public RepairCommand(MapRepair plugin) {
        this.plugin = plugin;
        this.blockDataHandler = plugin.getBlockDataHandler();
    }

    @Override
    public String getName() {
        return "repair";
    }

    @Override
    public String getDescription() {
        return "Repair the map";
    }

    @Override
    public String getSyntax() {
        return "/maprepair repair";
    }

    @Override
    public void performCommand(CommandSender commandSender, String[] args) {
        if (blockDataHandler == null) {
            commandSender.sendMessage("BlockDataHandler is not initialized!");
            return;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be run by a player!");
            return;
        }

        Player player = (Player) commandSender;
        String playerWorldName = player.getWorld().getName();

        ProcessConfigMessagesUtility.processMessage("messages.restoring", commandSender);

        Map<String, String> blockMap = blockDataHandler.getAllBlockData().get(playerWorldName);

        if (blockMap == null || blockMap.isEmpty()) {
            player.sendMessage("No data to repair in this world.");
            return;
        }

        // Iterate through each block's saved location and data
        for (Map.Entry<String, String> blockEntry : blockMap.entrySet()) {
            String locKey = blockEntry.getKey();
            String blockType = blockEntry.getValue();

            Location location = stringToLocation(playerWorldName, locKey);
            Material material = Material.getMaterial(blockType);

            if (location != null && material != null) {
                location.getBlock().setType(material);
            }
        }
        // After the loop, clear all the data for the world
        blockDataHandler.clearWorldData(playerWorldName);

        ProcessConfigMessagesUtility.processMessage("messages.restored", commandSender);
    }

    private Location stringToLocation(String worldName, String locKey) {
        String[] parts = locKey.split("_");
        if (parts.length != 3) {
            return null;
        }

        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            return new Location(plugin.getServer().getWorld(worldName), x, y, z);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
