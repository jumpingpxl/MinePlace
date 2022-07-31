package de.northernsi.mineplace.services.command;

import org.bukkit.entity.Player;

public abstract class PreProcessCommand {

	private final String[] aliases;

	protected PreProcessCommand(String... aliases) {
		this.aliases = aliases;
	}

	public abstract void execute(Player player, String prefix, String[] args);

	public String[] getAliases() {
		return this.aliases;
	}
}
