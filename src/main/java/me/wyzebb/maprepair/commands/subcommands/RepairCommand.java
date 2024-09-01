package me.wyzebb.maprepair.commands.subcommands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
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
        this.blockDataHandler = plugin.getBlockDataHandler(); // Access the BlockDataHandler instance
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

        else if (commandSender instanceof Player) {
            commandSender.sendMessage("Repair command received! Restoring blocks...");

            // Iterate through each world stored in blockDataHandler
            for (Map.Entry<String, Map<String, String>> worldEntry : blockDataHandler.getAllBlockData().entrySet()) {
                String worldName = worldEntry.getKey();
                Map<String, String> blockMap = worldEntry.getValue();

                // Iterate through each block's saved location and data
                for (Map.Entry<String, String> blockEntry : blockMap.entrySet()) {
                    String locKey = blockEntry.getKey();
                    String blockType = blockEntry.getValue();

                    // Convert the location string back to a Location object
                    Location location = stringToLocation(worldName, locKey);

                    // Convert the block type string back to Material
                    Material material = Material.getMaterial(blockType);

                    if (location != null && material != null) {
                        location.getBlock().setType(material);
                    }
                }
            }

            commandSender.sendMessage("All saved blocks have been restored.");
        }

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
