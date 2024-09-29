package me.wyzebb.maprepair.utility;

import me.wyzebb.maprepair.MapRepair;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ProcessConfigMessagesUtility {
    public static void processMessage(String langPath, CommandSender commandSender, int status) {
        MapRepair plugin = MapRepair.getPlugin(MapRepair.class);
        String color = plugin.getConfig().getString("color");
        String errorColor = plugin.getConfig().getString("error-color");
        String successColor = plugin.getConfig().getString("success-color");

        LanguageManager languageManager = plugin.getLanguageManager();
        FileConfiguration langConfig = languageManager.getLanguageFile();

        if (commandSender instanceof Player) {
            String msg;
            if (status == 1) {
                msg = errorColor;
            } else if (status == 2) {
                msg = successColor;
            } else {
                msg = color;
            }
            commandSender.sendMessage(msg + langConfig.getString(langPath, "Message key not found."));
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (status == 1) {
                plugin.getLogger().warning(langConfig.getString(langPath, "Message key not found."));
            } else {
                plugin.getLogger().info(langConfig.getString(langPath, "Message key not found."));
            }
        }
    }
}
