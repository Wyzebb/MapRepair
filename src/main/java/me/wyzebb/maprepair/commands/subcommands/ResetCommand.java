package me.wyzebb.maprepair.commands.subcommands;

import me.wyzebb.maprepair.MapRepair;
import org.bukkit.command.CommandSender;

public class ResetCommand extends SubCommand {

    private final MapRepair plugin;

    public ResetCommand(MapRepair plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public String getDescription() {
        return "Reset the map";
    }

    @Override
    public String getSyntax() {
        return "/map reset";
    }

    @Override
    public void performCommand(CommandSender commandSender, String[] args) {
        sendToConsole();
        commandSender.sendMessage("Reset command received!");
    }

    private void sendToConsole() {
        plugin.getLogger().info("Reset command");
    }
}