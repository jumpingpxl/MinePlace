package de.northernsi.mineplace.listeners.player;

import de.northernsi.mineplace.MinePlace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	private final MinePlace minePlace;

	public PlayerJoinListener(MinePlace minePlace) {
		this.minePlace = minePlace;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);

		Player player = event.getPlayer();
		this.minePlace.scoreboardController().setTeam(player);
		this.minePlace.tabListAccessor().sendTabPacket(player);
	}
}
