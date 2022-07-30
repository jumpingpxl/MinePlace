package de.northernsi.mineplace.listeners.player;

import de.northernsi.mineplace.misc.supplier.CooldownSupplier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
	    event.setQuitMessage(null);
	    for (CooldownSupplier.Type type : CooldownSupplier.Type.getValues()) {
		    CooldownSupplier.of(type).remove(event.getPlayer());
	    }
    }
}
