package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.services.command.Command;
import org.bukkit.entity.Player;

public class RankCommand extends Command {

    @Override
    protected void execute(Player player, String prefix, String[] args) {
        if (/*UserService.getInstance().getRank(
            (Player) commandSender) != Rank.ADMIN &&*/ !player.isOp()) {
            player.sendMessage("§cYou don't have the permission to execute this command!");
            return;
        }

        if (args.length < 2) {
            player.sendMessage("§cUsage: /rank <username> <rank>");
            return;
        }

        args[1] = args[1].toLowerCase();
        switch (args[1]) {
            case "admin":
            case "dev":
            case "mod":
            case "vip":
            case "guest":
                //UserService.getInstance().setRank(args[0], Rank.valueOf(args[1].toUpperCase()));
                //ScoreboardProvider.getInstance().setScoreboard();
                player.sendMessage("§aSet rank of §e" + args[0] + " §ato §e" + args[1]);
                break;
            default:
                player.sendMessage("§cUnknown rank");
                break;
        }

        //ScoreboardProvider.getInstance().setScoreboard();
    }
}