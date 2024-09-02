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

        // Handle the map initialisation
        blockData.computeIfAbsent(worldName, k -> new HashMap<>());

        // Save the block data
        Map<String, String> worldMap = blockData.get(worldName);
        worldMap.put(locKey, data);
    }

    private void saveAllData(File dataFolder) {
        File worldsFolder = new File(dataFolder, "worlds");
        if (!worldsFolder.exists()) {
            worldsFolder.mkdirs();
        }

        for (String worldName : blockData.keySet()) {
            File file = new File(worldsFolder, worldName + ".yml");
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
        File worldsFolder = new File(dataFolder, "worlds");
        if (!worldsFolder.exists()) return;

        for (File worldFile : worldsFolder.listFiles()) {
            if (worldFile.isFile() && worldFile.getName().endsWith(".yml")) {
                String worldName = worldFile.getName().replace(".yml", "");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(worldFile);

                Map<String, String> worldData = new HashMap<>();
                if (config.contains("blocks")) {
                    for (String key : config.getConfigurationSection("blocks").getKeys(false)) {
                        worldData.put(key, config.getString("blocks." + key));
                    }
                    blockData.put(worldName, worldData);
                }
            }
        }
    }

    private String locationToString(Location location) {
        return location.getBlockX() + "_" +
                location.getBlockY() + "_" +
                location.getBlockZ();
    }

    // Method to remove all block data for a world
    public void clearWorldData(String worldName) {
        blockData.remove(worldName);

        // Also clear the data from the corresponding YAML file
        File worldsFolder = new File(plugin.getDataFolder(), "worlds");
        File file = new File(worldsFolder, worldName + ".yml");

        if (file.exists()) {
            file.delete();  // Deletes the file
        }
    }


    // Add this method to expose blockData
    public Map<String, Map<String, String>> getAllBlockData() {
        return blockData;
    }
}
