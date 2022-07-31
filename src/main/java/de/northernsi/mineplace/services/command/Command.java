package de.northernsi.mineplace.services.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor {

	@Override
	public final boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command,
	                               String label, String[] args) {
		if (commandSender instanceof Player) {
			this.execute((Player) commandSender, label, args);
			return true;
		}

		return false;
	}

	protected abstract void execute(Player player, String prefix, String[] args);
}
