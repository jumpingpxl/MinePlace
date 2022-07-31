package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.MinePlace;
import de.northernsi.mineplace.services.command.PreProcessCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageCommand extends PreProcessCommand {

	public MessageCommand(MinePlace minePlace) {
		super("message", "msg", "tell", "whisper");
	}

	@Override
	public void execute(Player player, String prefix, String[] args) {
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage("§cThe player §e" + args[0] + "§c is not online.");
			return;
		}

		String message = String.join(" ", args);
		target.sendMessage("§8[§6MSG§8] §7" + player.getName() + " §8§l⇢ §7You §8> §f" + message);
		player.sendMessage("§8[§6MSG§8] §7You §8§l⇢ §7" + target.getName() + " §8> §f" + message);
	}
}
