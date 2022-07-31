package de.northernsi.mineplace.listeners.player;

import de.northernsi.mineplace.services.command.PreProcessCommandService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreProcessListener implements Listener {

	private final PreProcessCommandService preProcessCommandService;

	public PlayerCommandPreProcessListener(PreProcessCommandService preProcessCommandService) {
		this.preProcessCommandService = preProcessCommandService;
	}

	@EventHandler
	public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) {
			return;
		}

		String[] message = event.getMessage().trim().split(" ");
		String prefix = message[0].substring(1).toLowerCase();

		// Split prefix with ":" so f.e. that we don't need to check for "/bukkit:plugins" AND
		// "/plugins"
		if (prefix.contains(":")) {
			prefix = prefix.split(":")[1];
		}

		// This is more performant than Arrays.copyOfRange as that method uses reflections
		String[] args = new String[message.length - 1];
		for (int i = 1; i < message.length; i++) {
			args[i - 1] = message[i];
		}

		event.setCancelled(this.preProcessCommandService.handle(event.getPlayer(), prefix, args));
	}
}