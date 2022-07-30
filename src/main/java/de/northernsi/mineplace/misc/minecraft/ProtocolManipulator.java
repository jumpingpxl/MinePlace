package de.northernsi.mineplace.misc.minecraft;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.google.common.collect.Lists;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ProtocolManipulator {

	private final JavaPlugin javaPlugin;
	private final ProtocolManager protocol;

	public ProtocolManipulator(JavaPlugin javaPlugin) {
		this.javaPlugin = javaPlugin;

		this.protocol = ProtocolLibrary.getProtocolManager();
		this.registerPacketListeners();
	}

	public void registerPacketListeners() {
		this.protocol.addPacketListener(new ServerInfoPacketAdapter(this.javaPlugin));
	}

	private static class ServerInfoPacketAdapter extends PacketAdapter {

		private final List<WrappedGameProfile> fakeGameProfiles;

		public ServerInfoPacketAdapter(Plugin plugin) {
			super(plugin, PacketType.Status.Server.SERVER_INFO);

			this.fakeGameProfiles = Lists.newArrayList();
			this.fillFakeGameProfiles();
		}

		@Override
		public void onPacketSending(PacketEvent event) {
			WrappedServerPing wrappedServerPing = event.getPacket().getServerPings().read(0);
			wrappedServerPing.setVersionName("§c1.8 - 1.19");
			wrappedServerPing.setPlayers(this.fakeGameProfiles);
		}

		private void fillFakeGameProfiles() {
			this.fakeGameProfiles.add(new WrappedGameProfile("1", " "));
			this.fakeGameProfiles.add(
					new WrappedGameProfile("2", "        §e§lMine§6§lPlace §8» §7r/place in Minecraft "));
			this.fakeGameProfiles.add(
					new WrappedGameProfile("3", "                 §8» §emine§6place§7.space §8«       "));
			this.fakeGameProfiles.add(new WrappedGameProfile("4", " "));
			this.fakeGameProfiles.add(
					new WrappedGameProfile("5", "               §9discord.gg/4ttvN7m2SY               "));
			this.fakeGameProfiles.add(new WrappedGameProfile("6", " "));
		}
	}
}
