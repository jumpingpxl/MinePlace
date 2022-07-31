package de.northernsi.mineplace.commands;

import de.northernsi.mineplace.services.command.PreProcessCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VersionCommand extends PreProcessCommand {

	private final JavaPlugin javaPlugin;

	public VersionCommand(JavaPlugin javaPlugin) {
		super("plugins", "pl", "version", "ver", "v", "about", "bukkit", "spigot", "paper");

		this.javaPlugin = javaPlugin;
	}

	@Override
	public void execute(Player player, String prefix, String[] args) {
		TextComponent srcMessage = new TextComponent();
		srcMessage.setText("§7The source code is completely available on ");

		TextComponent srcLink = new TextComponent();
		srcLink.setText("§egithub.com/Northernside/MinePlace");
		srcLink.setClickEvent(
				new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Northernside/MinePlace"));
		srcLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder("§7Click to visit the GitHub repository.").create()));
		srcMessage.addExtra(srcLink);

		TextComponent versionMessage = new TextComponent();
		versionMessage.setText(
				"§7This server is running on§r §eMine§6Place§r §av" + this.javaPlugin.getDescription()
						.getVersion() + "§7.\n");
		versionMessage.addExtra(srcMessage);

		player.spigot().sendMessage(versionMessage);
	}
}
