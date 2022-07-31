package de.northernsi.mineplace;

import com.google.common.collect.Lists;
import de.northernsi.mineplace.commands.BypassCooldownCommand;
import de.northernsi.mineplace.commands.HelpCommand;
import de.northernsi.mineplace.commands.MessageCommand;
import de.northernsi.mineplace.commands.ModToolCommand;
import de.northernsi.mineplace.commands.PingCommand;
import de.northernsi.mineplace.commands.RankCommand;
import de.northernsi.mineplace.commands.RulesCommand;
import de.northernsi.mineplace.commands.TeamCommand;
import de.northernsi.mineplace.commands.VersionCommand;
import de.northernsi.mineplace.listeners.LabyModListener;
import de.northernsi.mineplace.listeners.WeatherChangeListener;
import de.northernsi.mineplace.listeners.block.BlockBreakListener;
import de.northernsi.mineplace.listeners.block.BlockPlaceListener;
import de.northernsi.mineplace.listeners.entity.EntityDamageListener;
import de.northernsi.mineplace.listeners.entity.EntitySpawnListener;
import de.northernsi.mineplace.listeners.entity.FoodLevelChangeListener;
import de.northernsi.mineplace.listeners.entity.PotionSplashListener;
import de.northernsi.mineplace.listeners.entity.ProjectileLaunchListener;
import de.northernsi.mineplace.listeners.player.AsyncPlayerChatListener;
import de.northernsi.mineplace.listeners.player.PlayerBukkitEmptyListener;
import de.northernsi.mineplace.listeners.player.PlayerCommandPreProcessListener;
import de.northernsi.mineplace.listeners.player.PlayerDropItemListener;
import de.northernsi.mineplace.listeners.player.PlayerInteractListener;
import de.northernsi.mineplace.listeners.player.PlayerJoinListener;
import de.northernsi.mineplace.listeners.player.PlayerQuitListener;
import de.northernsi.mineplace.misc.Task;
import de.northernsi.mineplace.misc.data.DataSupplier;
import de.northernsi.mineplace.misc.minecraft.BossBarController;
import de.northernsi.mineplace.misc.minecraft.InventoryController;
import de.northernsi.mineplace.misc.minecraft.ProtocolManipulator;
import de.northernsi.mineplace.misc.minecraft.ScoreboardController;
import de.northernsi.mineplace.misc.minecraft.TabListAccessor;
import de.northernsi.mineplace.services.BroadcastService;
import de.northernsi.mineplace.services.ConfigurationService;
import de.northernsi.mineplace.services.TeamService;
import de.northernsi.mineplace.services.UserService;
import de.northernsi.mineplace.services.command.PreProcessCommandService;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class MinePlace extends JavaPlugin {

	private final List<UUID> usersWithLabyMod = Lists.newArrayList();

	// Data
	private DataSupplier dataSupplier;
	private TeamService teamService;
	private UserService userService;

	// Minecraft
	private ScoreboardController scoreboardController;
	private TabListAccessor tabListAccessor;
	private ProtocolManipulator protocolManipulator;
	private InventoryController inventoryController;
	private BossBarController bossBarController;

	// Misc
	private BroadcastService broadcastService;
	private PreProcessCommandService preProcessCommandService;

	@Override
	public void onEnable() {
		long start = System.currentTimeMillis();
		System.out.println("> Enabling MinePlace...");

		Task.setPlugin(this);

		this.dataSupplier = new ConfigurationService(this);
		this.teamService = new TeamService(this);
		this.userService = new UserService(this);

		this.scoreboardController = new ScoreboardController(this);
		this.tabListAccessor = new TabListAccessor();
		this.protocolManipulator = new ProtocolManipulator(this);
		this.inventoryController = new InventoryController(this);
		this.bossBarController = new BossBarController(this);

		this.broadcastService = new BroadcastService(this);
		this.preProcessCommandService = new PreProcessCommandService();

		this.getServer().getMessenger().registerIncomingPluginChannel(this, "labymod3:main",
				new LabyModListener(this));

		PluginManager pluginManager = this.getServer().getPluginManager();
		pluginManager.registerEvents(new BlockBreakListener(), this);
		pluginManager.registerEvents(new BlockPlaceListener(), this);
		pluginManager.registerEvents(new PlayerInteractListener(this), this);
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		pluginManager.registerEvents(new WeatherChangeListener(), this);
		pluginManager.registerEvents(new PlayerDropItemListener(), this);
		pluginManager.registerEvents(new AsyncPlayerChatListener(this.userService), this);
		pluginManager.registerEvents(new PlayerQuitListener(), this);
		pluginManager.registerEvents(new EntityDamageListener(), this);
		pluginManager.registerEvents(new EntitySpawnListener(), this);
		pluginManager.registerEvents(new FoodLevelChangeListener(), this);
		pluginManager.registerEvents(new PlayerCommandPreProcessListener(this.preProcessCommandService),
				this);
		pluginManager.registerEvents(new PlayerBukkitEmptyListener(), this);
		pluginManager.registerEvents(new PotionSplashListener(), this);
		pluginManager.registerEvents(new ProjectileLaunchListener(), this);

		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("team").setExecutor(new TeamCommand());
		this.getCommand("modtool").setExecutor(new ModToolCommand());
		this.getCommand("rank").setExecutor(new RankCommand());
		this.getCommand("bypasscooldown").setExecutor(new BypassCooldownCommand());
		this.getCommand("rules").setExecutor(new RulesCommand());

		this.preProcessCommandService.register(new HelpCommand());
		this.preProcessCommandService.register(new MessageCommand(this));
		this.preProcessCommandService.register(new VersionCommand(this));

		this.broadcastService.addMessage(new ComponentBuilder(
				"§e§lMine§6§lPlace §9» §7View our live world map at ").append("§emap.mineplace.space")
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://map.mineplace.space"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						TextComponent.fromLegacyText("§7Click to visit the live world map.")))
				.create());

		System.out.println(
				"> MinePlace has been enabled. Took " + (System.currentTimeMillis() - start) + "ms.");
	}

	@Override
	public void onDisable() {
		System.out.println("> MinePlace has been disabled.");
	}

	public List<UUID> getUsersWithLabyMod() {
		return this.usersWithLabyMod;
	}

	public DataSupplier dataSupplier() {
		return this.dataSupplier;
	}

	public TeamService teamService() {
		return this.teamService;
	}

	public UserService userService() {
		return this.userService;
	}

	public ScoreboardController scoreboardController() {
		return this.scoreboardController;
	}

	public TabListAccessor tabListAccessor() {
		return this.tabListAccessor;
	}

	public ProtocolManipulator protocolManipulator() {
		return this.protocolManipulator;
	}

	public InventoryController inventoryController() {
		return this.inventoryController;
	}

	public PreProcessCommandService preProcessCommandService() {
		return this.preProcessCommandService;
	}

	public BossBarController bossBarController() {
		return this.bossBarController;
	}
}