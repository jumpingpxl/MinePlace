// Credits: Zeichenfolge

package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.services.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand extends Command {

	@Override
	protected void execute(Player player, String prefix, String[] args) {
		if (args.length == 0) {
			player.sendMessage("§aYour ping: §e" + ((CraftPlayer) player).getHandle().ping + "ms");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage("§cPlayer not found");
			return;
		}

		CraftPlayer craftTarget = (CraftPlayer) target;
		player.sendMessage("§e" + target.getName() + "§a" + (!target.getName().endsWith(
				"s".toLowerCase()) ? "'s" : "'") + " ping is §e" + craftTarget.getHandle().ping + "ms");
	}
}