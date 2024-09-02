package me.wyzebb.maprepair.utility;

import me.wyzebb.maprepair.MapRepair;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ProcessConfigMessagesUtility {
    public static void processMessage(String langPath, CommandSender commandSender, boolean isError) {
        MapRepair plugin = MapRepair.getPlugin(MapRepair.class);
        String color = plugin.getConfig().getString("color");
        String errorColor = plugin.getConfig().getString("error-color");

        LanguageManager languageManager = plugin.getLanguageManager();
        FileConfiguration langConfig = languageManager.getLanguageFile();

        if (commandSender instanceof Player) {
            String msg;
            if (isError) {
                msg = errorColor;
            } else {
                msg = color;
            }
            commandSender.sendMessage(msg + langConfig.getString(langPath, "Message key not found."));
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (isError) {
                plugin.getLogger().warning(langConfig.getString(langPath, "Message key not found."));
            } else {
                plugin.getLogger().info(langConfig.getString(langPath, "Message key not found."));
            }
        }
    }
}
