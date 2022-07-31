package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.services.command.Command;
import org.bukkit.entity.Player;

public class ModToolCommand extends Command {

    @Override
    protected void execute(Player player, String prefix, String[] args) {
        //if (UserService.getInstance().getRank(player) != Rank.MOD
        //    && UserService.getInstance().getRank(player) != Rank.ADMIN) {
        player.sendMessage("§cYou don't have the permission to execute this command!");
        return;
        // }

        //InventoryPages.changePage(666, player.getInventory());
        //player.getInventory().setHeldItemSlot(4);
    }
}
