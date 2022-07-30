package de.northernsi.mineplace.listeners;

import de.northernsi.mineplace.MinePlace;
import de.northernsi.mineplace.misc.labymod.LabyModProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class LabyModListener implements PluginMessageListener {

	private final MinePlace minePlace;

	public LabyModListener(MinePlace minePlace) {
		this.minePlace = minePlace;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("labymod3:main")) {
			return;
		}

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

		ByteBuf buf = Unpooled.wrappedBuffer(message);
		String key = LabyModProtocol.readString(buf, Short.MAX_VALUE);
        String json = LabyModProtocol.readString(buf, Short.MAX_VALUE);

        // LabyMod user joins the server
        if (key.equals("INFO")) {
	        this.minePlace.getUsersWithLabyMod().add(player.getUniqueId());
        }
    }
}