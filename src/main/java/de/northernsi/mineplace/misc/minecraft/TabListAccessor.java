package de.northernsi.mineplace.misc.minecraft;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TabListAccessor {

	private final Field fieldA;
	private final Field fieldB;

	public TabListAccessor() {
		Class<?> packetClass = PacketPlayOutPlayerListHeaderFooter.class;

		try {
			this.fieldA = packetClass.getDeclaredField("a");
			this.fieldA.setAccessible(true);

			this.fieldB = packetClass.getDeclaredField("b");
			this.fieldB.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendTabPacket(Player player) {
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

		try {
			this.fieldA.set(packet, this.headerText(player));
			this.fieldB.set(packet, this.footerText(player));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	private IChatBaseComponent headerText(Player player) {
		String headerText = "&e&lMine&6&lPlace";
		return this.serialize(headerText);
	}

	private IChatBaseComponent footerText(Player player) {
		String footerText = " &7Made with &c❤ &7by Northernside ";
		return this.serialize(footerText);
	}

	private IChatBaseComponent serialize(String string) {
		return IChatBaseComponent.ChatSerializer.a(
				"{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', string) + "\"}");
	}
}
