package me.wyzebb.maprepair.commands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.commands.subcommands.RepairCommand;
import me.wyzebb.maprepair.commands.subcommands.SubCommand;
import me.wyzebb.maprepair.utility.SendHelpMsgUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();
    private final MapRepair plugin;

    public CommandManager(MapRepair plugin){
        this.plugin = plugin;
        // Ensure the RepairCommand is initialized with the plugin instance
        subcommands.add(new RepairCommand(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0){
            for (SubCommand subcommand : getSubcommands()) {
                if (args[0].equalsIgnoreCase(subcommand.getName())) {
                    subcommand.performCommand(commandSender, args);
                    return true; // Exit after finding the command
                }
            }
            SendHelpMsgUtility.sendHelpMessage(commandSender, plugin);
        } else {
            SendHelpMsgUtility.sendHelpMessage(commandSender, plugin);
        }
        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList<String> suggestions = new ArrayList<>();
            for (SubCommand subcommand : getSubcommands()) {
                suggestions.add(subcommand.getName());
            }
            return suggestions;
        } else if (args.length >= 2) {
            return new ArrayList<>(); // Empty list for further arguments
        }
        return new ArrayList<>();
    }
}
