package de.northernsi.mineplace.misc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Task {

	private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
	private static JavaPlugin plugin;

	private final Runnable runnable;

	private Task(Runnable runnable) {
		this.runnable = runnable;
	}

	public static Task of(Runnable runnable) {
		return new Task(runnable);
	}

	public static void setPlugin(JavaPlugin plugin) {
		if (Task.plugin != null) {
			throw new UnsupportedOperationException("Plugin is already set");
		}

		Task.plugin = plugin;
	}

	public void run() {
		SCHEDULER.runTask(plugin, this.runnable);
	}

	public void runAsync() {
		SCHEDULER.runTaskAsynchronously(plugin, this.runnable);
	}

	public BukkitTask runLater(long delay, Unit unit) {
		return SCHEDULER.runTaskLater(plugin, this.runnable, unit.convert(delay));
	}

	public BukkitTask runLaterAsync(long delay, Unit unit) {
		return SCHEDULER.runTaskLaterAsynchronously(plugin, this.runnable, unit.convert(delay));
	}

	public BukkitTask runRepeat(long delay, long interval, Unit unit) {
		return SCHEDULER.runTaskTimer(plugin, this.runnable, unit.convert(delay),
				unit.convert(interval));
	}

	public BukkitTask runRepeatAsync(long delay, long interval, Unit unit) {
		return SCHEDULER.runTaskTimerAsynchronously(plugin, this.runnable, unit.convert(delay),
				unit.convert(interval));
	}

	public enum Unit {
		TICKS(1),
		SECONDS(20),
		MINUTES(1200),
		HOURS(72000);

		private final int ticks;

		Unit(int ticks) {
			this.ticks = ticks;
		}

		public int getTicks() {
			return this.ticks;
		}

		public long convert(long amount) {
			return amount * this.ticks;
		}
	}
}
