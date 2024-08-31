package me.wyzebb.maprepair.commands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.commands.subcommands.ResetCommand;
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
        subcommands.add(new ResetCommand(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0){
            for (int i = 0; i < getSubcommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                    getSubcommands().get(i).performCommand(commandSender, args);
                }
            }
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

            for (int i = 0; i < getSubcommands().size(); i++){
                suggestions.add(getSubcommands().get(i).getName());
            }

            return suggestions;
        } else if (args.length >= 2) {
            return new ArrayList<>() {};
        }

        return new ArrayList<>() {};
    }
}
