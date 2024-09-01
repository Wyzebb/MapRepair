package me.wyzebb.maprepair.utility;

import me.wyzebb.maprepair.MapRepair;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

    private final MapRepair plugin;
    private final Map<String, FileConfiguration> languages = new HashMap<>();
    private String defaultLanguage;

    public LanguageManager(MapRepair plugin) {
        this.plugin = plugin;
        loadConfig();
        copyDefaultLanguages();
        loadLanguages();
    }

    private void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        defaultLanguage = config.getString("language", "en_US"); // Default to "en_US" if not set
    }

    private void copyDefaultLanguages() {
        String[] defaultLanguages = {"en_US.yml", "fr_FR.yml"}; // List all default languages you provide

        File languagesFolder = new File(plugin.getDataFolder(), "lang");
        if (!languagesFolder.exists()) {
            languagesFolder.mkdirs();
        }

        for (String langFileName : defaultLanguages) {
            File langFile = new File(languagesFolder, langFileName);
            if (!langFile.exists()) {
                try (InputStream in = plugin.getResource("lang/" + langFileName)) { // Assuming you put your lang files in src/main/resources/lang/
                    if (in != null) {
                        Files.copy(in, langFile.toPath());
                        plugin.getLogger().info("Copied default language file: " + langFileName);
                    } else {
                        plugin.getLogger().warning("Language file not found in JAR: " + langFileName);
                    }
                } catch (IOException e) {
                    plugin.getLogger().severe("Failed to copy language file: " + langFileName);
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadLanguages() {
        File languagesFolder = new File(plugin.getDataFolder(), "lang");
        File[] files = languagesFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".yml")) {
                    String lang = file.getName().replace(".yml", "");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    languages.put(lang, config);
                }
            }
        }
    }

    public FileConfiguration getLanguageFile() {
        return languages.getOrDefault(defaultLanguage, languages.get("en_US")); // Default to English if language not found
    }
}