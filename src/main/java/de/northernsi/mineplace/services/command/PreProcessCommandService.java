package de.northernsi.mineplace.services.command;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import java.util.List;

public class PreProcessCommandService {

	private final List<PreProcessCommand> commands;

	public PreProcessCommandService() {
		this.commands = Lists.newArrayList();
	}

	public void register(PreProcessCommand command) {
		this.commands.add(command);
	}

	public boolean handle(Player player, String prefix, String[] args) {
		for (PreProcessCommand command : this.commands) {
			for (String alias : command.getAliases()) {
				if (alias.equals(prefix)) {
					command.execute(player, prefix, args);
					return true;
				}
			}
		}

		return false;
	}
}
