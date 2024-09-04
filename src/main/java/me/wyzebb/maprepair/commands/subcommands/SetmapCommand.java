package me.wyzebb.maprepair.commands.subcommands;

import me.wyzebb.maprepair.MapRepair;
import me.wyzebb.maprepair.utility.BlockDataHandler;
import me.wyzebb.maprepair.utility.LanguageManager;
import me.wyzebb.maprepair.utility.ProcessConfigMessagesUtility;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetmapCommand extends SubCommand {

    private final BlockDataHandler blockDataHandler;
    private final LanguageManager languageManager;

    public SetmapCommand(MapRepair plugin) {
        this.blockDataHandler = plugin.getBlockDataHandler();
        this.languageManager = plugin.getLanguageManager();
    }

    @Override
    public String getName() {
        return "setmap";
    }

    @Override
    public String getDescription() {
        return languageManager.getLanguageFile().getString("commands.setmap-cmd.description");
    }

    @Override
    public String getUsage() {
        return "/maprepair setmap";
    }

    @Override
    public void performCommand(CommandSender commandSender, String[] args) {
        if (blockDataHandler == null) {
            ProcessConfigMessagesUtility.processMessage("messages.error", commandSender, true);
            return;
        }
        if (!(commandSender instanceof Player player)) {
            ProcessConfigMessagesUtility.processMessage("messages.player-cmd", commandSender, true);
            return;
        }

        String playerWorldName = player.getWorld().getName();
        blockDataHandler.clearWorldData(playerWorldName);

        ProcessConfigMessagesUtility.processMessage("messages.setmap-success", commandSender, false);
    }
}
