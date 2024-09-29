package me.wyzebb.maprepair.commands.subcommands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.LanguageManager;
import me.wyzebb.maprepair.utility.ProcessConfigMessagesUtility;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    private final MapRepair plugin;
    private final LanguageManager languageManager;

    public ReloadCommand(MapRepair plugin) {
        this.plugin = plugin;
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return languageManager.getLanguageFile().getString("commands.reload-cmd.description");
    }

    @Override
    public String getUsage() {
        return "/maprepair reload";
    }

    @Override
    public void performCommand(CommandSender commandSender, String[] args) {
        plugin.reloadConfig();

        ProcessConfigMessagesUtility.processMessage("messages.reload-success", commandSender, 2);
    }
}
