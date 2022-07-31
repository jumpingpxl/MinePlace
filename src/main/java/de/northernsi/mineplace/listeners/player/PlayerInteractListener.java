package de.northernsi.mineplace.listeners.player;

import de.northernsi.mineplace.MinePlace;
import de.northernsi.mineplace.misc.minecraft.InventoryController;
import de.northernsi.mineplace.misc.supplier.CooldownSupplier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class PlayerInteractListener implements Listener {

	private static final int PLACE_Y = 0;

	private final CooldownSupplier cooldownSupplier;
	private final MinePlace minePlace;

	public PlayerInteractListener(MinePlace minePlace) {
		this.minePlace = minePlace;

		this.cooldownSupplier = CooldownSupplier.of(CooldownSupplier.Type.PLACE);
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		ItemStack itemStack = event.getItem();
		if (itemStack == null) {
			return;
		}

		Action action = event.getAction();
		Player player = event.getPlayer();
		InventoryController inventoryController = this.minePlace.inventoryController();
		if (action != Action.LEFT_CLICK_BLOCK && action != Action.RIGHT_CLICK_BLOCK
				|| !inventoryController.isPlaceableBlock(player, itemStack)) {
			return;
		}

		Block clickedBlock = event.getClickedBlock();
		Location clickedLocation = clickedBlock.getLocation();
		if (clickedLocation.getBlockY() != PLACE_Y || this.cooldownSupplier.has(player)) {
			return;
		}

		World world = event.getPlayer().getWorld();
		Block block = world.getBlockAt(clickedLocation);
		block.setType(Material.WOOL);
		BlockState blockState = block.getState();
		blockState.setData(new Wool(((Wool) itemStack.getData()).getColor()));
		blockState.update();

		//TODO event & lastplaced

		this.cooldownSupplier.add(player);
		this.minePlace.bossBarController().sendBossBar(this.cooldownSupplier, player, BarColor.BLUE,
				seconds -> "§cPlease wait " + seconds + " second" + (seconds == 1 ? "" : "s") + " to "
						+ "place another block.");

		//todo extra items
	}
}