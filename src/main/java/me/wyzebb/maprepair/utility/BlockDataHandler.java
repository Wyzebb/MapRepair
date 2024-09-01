package me.wyzebb.maprepair.utility;

import me.wyzebb.maprepair.MapRepair;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockDataHandler {
    private Map<String, Map<String, String>> blockData;

    private final MapRepair plugin;

    public BlockDataHandler(MapRepair plugin) {
        this.plugin = plugin;
    }

    public void setupServerStartData() {
        blockData = new HashMap<>();
        loadAllData(plugin.getDataFolder());
    }

    public void serverShut() {
        saveAllData(plugin.getDataFolder());
    }

    public void saveBlockData(Location location, String data) {
        String worldName = location.getWorld().getName();
        String locKey = locationToString(location);

        // Handle the map initialization
        if (!blockData.containsKey(worldName)) {
            blockData.put(worldName, new HashMap<>());
        }

        // Save the block data
        Map<String, String> worldMap = blockData.get(worldName);
        worldMap.put(locKey, data);
    }

    private void saveAllData(File dataFolder) {
        for (String worldName : blockData.keySet()) {
            File worldFolder = new File(dataFolder, worldName);
            if (!worldFolder.exists()) {
                worldFolder.mkdirs();
            }

            File file = new File(worldFolder, "blocks.yml");
            YamlConfiguration config = new YamlConfiguration();

            for (Map.Entry<String, String> entry : blockData.get(worldName).entrySet()) {
                config.set("blocks." + entry.getKey(), entry.getValue());
            }

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadAllData(File dataFolder) {
        if (!dataFolder.exists()) return;

        for (File worldFolder : dataFolder.listFiles()) {
            if (worldFolder.isDirectory()) {
                File file = new File(worldFolder, "blocks.yml");
                if (file.exists()) {
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    Map<String, String> worldData = new HashMap<>();
                    for (String key : config.getConfigurationSection("blocks").getKeys(false)) {
                        worldData.put(key, config.getString("blocks." + key));
                    }
                    blockData.put(worldFolder.getName(), worldData);
                }
            }
        }
    }

    private String locationToString(Location location) {
        return location.getBlockX() + "_" +
                location.getBlockY() + "_" +
                location.getBlockZ();
    }

    // Add this method to expose blockData
    public Map<String, Map<String, String>> getAllBlockData() {
        return blockData;
    }
}
