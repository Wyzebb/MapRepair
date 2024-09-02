package me.wyzebb.maprepair.utility;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.commands.CommandManager;
import me.wyzebb.maprepair.commands.subcommands.SubCommand;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SendHelpMsgUtility {

    public static void sendHelpMessage(CommandSender commandSender, MapRepair plugin) {
        CommandManager cmdManager = new CommandManager(plugin);
        List<SubCommand> subcommands = cmdManager.getSubcommands();

        ArrayList<String> messageLines = new ArrayList<>();
        messageLines.add("§c--------------------------------");
        for (SubCommand subcommand : subcommands) {
            messageLines.add("§c§l" + subcommand.getUsage() + " - §e" + subcommand.getDescription());
        }
        messageLines.add("§c--------------------------------");

        if (commandSender instanceof Player p) {
            for (String line : messageLines) {
                p.sendMessage(line);
            }
        } else if (commandSender instanceof BlockCommandSender b) {
            for (String line : messageLines) {
                b.sendMessage(line);
            }
        } else {
            for (String line : messageLines) {
                plugin.getLogger().info(line);
            }
        }
    }
}
