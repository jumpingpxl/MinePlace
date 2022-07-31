package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.services.command.Command;
import org.bukkit.entity.Player;

public class RulesCommand extends Command {

	@Override
	protected void execute(Player player, String prefix, String[] args) {
		player.sendMessage(
				"§aOur building and general behavior rules are accessible here: §ehttps://mineplace"
						+ ".space/rules");
	}
}
