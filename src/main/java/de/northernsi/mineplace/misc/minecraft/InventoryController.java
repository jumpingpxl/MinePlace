package de.northernsi.mineplace.misc.minecraft;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static org.bukkit.DyeColor.*;

public class InventoryController {

	private static final String METADATA_KEY = "mineplace_page";

	private static final Item BACK_ITEM = new Item(Material.ARROW, "§8« §7Back", ItemAction.BACK);
	private static final Item NEXT_ITEM = new Item(Material.ARROW, "§8» §7Next", ItemAction.NEXT);

	private static final Item LAST_PLACED_ITEM = new Item(Material.NAME_TAG, "§bLast Placed By",
			ItemAction.LAST_PLACED);
	private static final Item CENSOR_ITEM = new Item(Material.LAVA_BUCKET, "§cCensor",
			ItemAction.CENSOR);
	private static final Item POSITION_ITEM = new Item(Material.BLAZE_ROD, "§aCensor Position",
			ItemAction.POSITION);

	private static final Item[][] WOOL_POSITIONS = {{Item.of(WHITE), Item.of(SILVER), Item.of(GRAY),
			Item.of(BLACK), Item.of(RED), Item.of(BROWN), Item.of(ORANGE), Item.of(YELLOW)}, {Item.of(
			LIME), Item.of(GREEN), Item.of(LIGHT_BLUE), Item.of(CYAN), Item.of(BLUE), Item.of(PURPLE),
			Item.of(MAGENTA), Item.of(PINK)}};

	private final JavaPlugin javaPlugin;

	public InventoryController(JavaPlugin javaPlugin) {
		this.javaPlugin = javaPlugin;
	}

	public void setDefaultPage(Player player) {
		this.setPage(player, 0);
	}

	public void setPage(Player player, int index) {
		if (index > WOOL_POSITIONS.length - 1) {
			index = 0;
		}

		player.setMetadata(METADATA_KEY, new FixedMetadataValue(this.javaPlugin, index));

		Item[] items = WOOL_POSITIONS[index];
		int startPosition = index == 0 ? 0 : 1;
		int endPosition = index == WOOL_POSITIONS.length - 1 ? 8 : 7;

		PlayerInventory inventory = player.getInventory();
		for (int i = startPosition; i < endPosition; i++) {
			inventory.setItem(i, items[i - startPosition].get());
		}

		if (startPosition == 1) {
			inventory.setItem(0, BACK_ITEM.get());
		}

		if (endPosition == 7) {
			inventory.setItem(8, NEXT_ITEM.get());
		}
	}

	public boolean isPlaceableBlock(Player player, ItemStack itemStack) {
		int currentPage = this.getCurrentPage(player);
		if (currentPage == Integer.MAX_VALUE) {
			return false;
		}

		Item[] items = WOOL_POSITIONS[currentPage];
		for (Item item : items) {
			if (item.get() == itemStack) {
				return true;
			}
		}

		return false;
	}

	public ItemAction getItemAction(Player player, ItemStack itemStack) {
		int currentPage = this.getCurrentPage(player);
		if (currentPage == Integer.MAX_VALUE) {
			if (NEXT_ITEM.get() == itemStack) {
				return ItemAction.NEXT;
			}

			if (BACK_ITEM.get() == itemStack) {
				return ItemAction.BACK;
			}

			return null;
		}

		Item[] items = WOOL_POSITIONS[currentPage];
		for (Item item : items) {
			if (item.get() == itemStack) {
				return ItemAction.PLACEABLE;
			}
		}

		return null;
	}

	public void setModerationPage(Player player) {
		player.setMetadata(METADATA_KEY, new FixedMetadataValue(this.javaPlugin, Integer.MAX_VALUE));
		PlayerInventory inventory = player.getInventory();

		inventory.setItem(0, BACK_ITEM.get());
		inventory.setItem(2, LAST_PLACED_ITEM.get());
		inventory.setItem(4, POSITION_ITEM.get());
		inventory.setItem(7, CENSOR_ITEM.get());
	}

	public int getCurrentPage(Player player) {
		int currentPage = 0;
		List<MetadataValue> metadataValues = player.getMetadata(METADATA_KEY);
		for (MetadataValue metadata : metadataValues) {
			if (metadata.value() instanceof Integer) {
				currentPage = metadata.asInt();
				break;
			}
		}

		return currentPage;
	}

	public enum ItemAction {
		BACK,
		NEXT,
		LAST_PLACED,
		CENSOR,
		POSITION,
		PLACEABLE
	}

	public static class Item {

		private final ItemStack itemStack;
		private final ItemAction action;

		private Item(Material material, String name, ItemAction action) {
			this(material, name, null, action);
		}

		private Item(DyeColor dyeColor) {
			this(null, null, dyeColor, ItemAction.PLACEABLE);
		}

		private Item(Material material, String name, DyeColor dyeColor, ItemAction action) {
			this.action = action;
			if (dyeColor != null) {
				this.itemStack = new Wool(dyeColor).toItemStack(1);
			} else {
				this.itemStack = new ItemStack(material, 1);
				ItemMeta itemMeta = this.itemStack.getItemMeta();
				itemMeta.setDisplayName(name);
				this.itemStack.setItemMeta(itemMeta);
			}
		}

		private static Item of(DyeColor dyeColor) {
			return new Item(dyeColor);
		}

		public ItemStack get() {
			return this.itemStack;
		}
	}
}
