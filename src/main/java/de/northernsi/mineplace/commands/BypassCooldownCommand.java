package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.listeners.player.PlayerInteractListener;
import de.northernsi.mineplace.services.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class BypassCooldownCommand extends Command {

	public static ArrayList<UUID> bypassingUsers = new ArrayList<UUID>();

	@Override
	protected void execute(Player player, String prefix, String[] args) {
		//if (UserService.getInstance().getRank(player) != Rank.MOD
		//	&& UserService.getInstance().getRank(player) != Rank.ADMIN) {
		player.sendMessage("§cYou don't have the permission to execute this command!");
		//	return true;
		//}

		if (bypassingUsers.contains(player.getUniqueId())) {
			bypassingUsers.remove(player.getUniqueId());
			player.sendMessage("§aYou are no longer bypassing the cooldown.");
			return;
		}

		bypassingUsers.add(player.getUniqueId());
		PlayerInteractListener.cooldownMap.remove(player.getUniqueId());
		player.sendMessage("§aYou are now bypassing the cooldown.");
	}
}
