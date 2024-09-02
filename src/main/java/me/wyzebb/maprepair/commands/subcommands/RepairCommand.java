package me.wyzebb.maprepair.commands.subcommands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import me.wyzebb.maprepair.utility.ProcessConfigMessagesUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class RepairCommand extends SubCommand {

    private final MapRepair plugin;
    private final BlockDataHandler blockDataHandler;
    private final LanguageManager languageManager;

    public RepairCommand(MapRepair plugin) {
        this.plugin = plugin;
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public String getName() {
        return languageManager.getLanguageFile().getString("commands.repair-cmd.name");
    }

    @Override
    public String getDescription() {
        return languageManager.getLanguageFile().getString("commands.repair-cmd.description");
    }

    @Override
    public String getUsage() {
        return languageManager.getLanguageFile().getString("commands.repair-cmd.usage");
    }

    @Override
    public void performCommand(CommandSender commandSender, String[] args) {
        if (blockDataHandler == null) {
            ProcessConfigMessagesUtility.processMessage("messages.error", commandSender, true);
            return;
        }
        if (!(commandSender instanceof Player player)) {
            ProcessConfigMessagesUtility.processMessage("messages.player-cmd", commandSender, true);
            return;
        }

        String playerWorldName = player.getWorld().getName();

        ProcessConfigMessagesUtility.processMessage("messages.repairing", commandSender, false);

        Map<String, String> blockMap = blockDataHandler.getAllBlockData().get(playerWorldName);

        if (blockMap == null || blockMap.isEmpty()) {
            ProcessConfigMessagesUtility.processMessage("messages.nothing-to-repair", player, true);
            return;
        }

        // Iterate through every block's saved location and data
        for (Map.Entry<String, String> blockEntry : blockMap.entrySet()) {
            String locKey = blockEntry.getKey();
            String blockType = blockEntry.getValue();

            Location location = stringToLocation(playerWorldName, locKey);
            Material material = Material.getMaterial(blockType);

            if (location != null && material != null) {
                location.getBlock().setType(material);
            }
        }

        blockDataHandler.clearWorldData(playerWorldName);

        ProcessConfigMessagesUtility.processMessage("messages.repaired", commandSender, false);
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
            plugin.getLogger().warning(languageManager.getLanguageFile().getString("messages.error"));
            return null;
        }
    }
}
