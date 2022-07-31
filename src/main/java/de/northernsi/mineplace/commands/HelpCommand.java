package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.services.command.PreProcessCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class HelpCommand extends PreProcessCommand {

	public HelpCommand() {
		super("me", "help", "h", "?", "commands", "mineplace", "mp", "server");
	}

	@Override
	public void execute(Player player, String prefix, String[] args) {
		player.sendMessage("§8[§eMine§6Place§8]§r §6Information about MinePlace");
		player.sendMessage("§e/ping§r §b<user>§r §7Shows your or another users' network latency");
		player.sendMessage("§e/team§r §b<arguments>§r §7Manage build teams");

		TextComponent moreInfo = new TextComponent();
		moreInfo.setText("§7Further information on our§r §9Discord§7:§r ");

		TextComponent discordLink = new TextComponent();
		discordLink.setText("§ediscord.gg/4ttvN7m2SY");
		discordLink.setClickEvent(
				new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/4ttvN7m2SY"));
		discordLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder("§7Click to visit the Discord server.").create()));
		moreInfo.addExtra(discordLink);

		TextComponent websiteInfo = new TextComponent();
		websiteInfo.setText("§7Visit our§r §bwebsite§7:§r ");

		TextComponent websiteLink = new TextComponent();
		websiteLink.setText("§emineplace.space");
		websiteLink.setClickEvent(
				new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mineplace.space"));
		websiteLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder("§7Click to visit the website.").create()));
		websiteInfo.addExtra(websiteLink);

		player.spigot().sendMessage(moreInfo);
		player.spigot().sendMessage(websiteInfo);
		return;
	}
}
