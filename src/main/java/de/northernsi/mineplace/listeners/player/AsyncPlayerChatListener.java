package de.northernsi.mineplace.listeners.player;

import de.northernsi.mineplace.misc.Constants;
import de.northernsi.mineplace.misc.Rank;
import de.northernsi.mineplace.misc.supplier.CooldownSupplier;
import de.northernsi.mineplace.services.UserService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

	private final CooldownSupplier cooldownSupplier;
	private final UserService userService;

	public AsyncPlayerChatListener(UserService userService) {
		this.userService = userService;

		this.cooldownSupplier = CooldownSupplier.of(CooldownSupplier.Type.CHAT);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		Rank rank = this.userService.getRank(event.getPlayer());
		switch (rank) {
			case ADMIN:
			case MOD:
				break;
			default:
				if (this.cooldownSupplier.has(player)) {
					event.setCancelled(true);
					player.sendMessage("§cDo not spam.");
					return;
				} else {
					this.cooldownSupplier.add(player);
				}

				break;
		}

		String message = ChatColor.translateAlternateColorCodes('&',
				event.getMessage().replace("%", "%%"));
		if (rank.getPrefix() == null) {
			event.setFormat(String.format(Constants.CHAT_WITHOUT_PREFIX, player.getName(), message));
		} else {
			event.setFormat(
					String.format(Constants.CHAT_WITH_PREFIX, rank.getPrimaryColor(), rank.getPrefix(),
							player.getName(), message));
		}
	}
}