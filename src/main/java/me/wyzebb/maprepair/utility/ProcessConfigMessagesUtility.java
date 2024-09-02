package me.wyzebb.maprepair.utility;

import me.wyzebb.maprepair.MapRepair;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ProcessConfigMessagesUtility {
    public static void processMessage(String langPath, CommandSender commandSender) {
        MapRepair plugin = MapRepair.getPlugin(MapRepair.class);
        String color = plugin.getConfig().getString("color");

        LanguageManager languageManager = plugin.getLanguageManager();
        FileConfiguration langConfig = languageManager.getLanguageFile();

        if (commandSender instanceof Player) {
            commandSender.sendMessage(color + langConfig.getString(langPath, "Message key not found."));
        }

        if (commandSender instanceof ConsoleCommandSender) {
            plugin.getLogger().info(langConfig.getString(langPath, "Message key not found."));
        }
    }
}
