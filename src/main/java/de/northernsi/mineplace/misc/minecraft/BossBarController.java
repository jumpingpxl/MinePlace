package de.northernsi.mineplace.misc.minecraft;

import de.northernsi.mineplace.misc.Task;
import de.northernsi.mineplace.misc.supplier.CooldownSupplier;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.IntFunction;

public class BossBarController {

	private final JavaPlugin javaPlugin;

	public BossBarController(JavaPlugin javaPlugin) {
		this.javaPlugin = javaPlugin;
	}

	public BossBar sendBossBar(Player player, String text, BarColor color) {
		BossBar bossBar = this.javaPlugin.getServer().createBossBar(text, color, BarStyle.SOLID);
		bossBar.addPlayer(player);
		return bossBar;
	}

	public BossBar sendBossBar(CooldownSupplier cooldownSupplier, Player player, BarColor color,
	                           IntFunction<String> textSupplier) {
		long remainingTime = cooldownSupplier.remainingTime(player);
		if (remainingTime == 0) {
			return null;
		}

		int remainingSeconds = (int) (remainingTime / 1000);
		BossBar bossBar = this.javaPlugin.getServer().createBossBar(
				textSupplier.apply(remainingSeconds), color, BarStyle.SEGMENTED_20);
		bossBar.addPlayer(player);
		bossBar.setProgress(20.0 / remainingSeconds);
		Task.of(task -> {
			long remaining = cooldownSupplier.remainingTime(player);
			if (remaining == 0) {
				bossBar.removePlayer(player);
				task.cancel();
			}

			int seconds = (int) (remainingTime / 1000);
			bossBar.setProgress(20.0 / remainingSeconds);
			bossBar.setTitle(textSupplier.apply(remainingSeconds));
		}).runRepeat(10, 10, Task.Unit.TICKS);

		return bossBar;
	}
}
