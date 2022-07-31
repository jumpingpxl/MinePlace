package de.northernsi.mineplace.misc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class Task {

	private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
	private static JavaPlugin plugin;

	private final Consumer<Task> task;
	private State state;
	private BukkitTask bukkitTask;

	private Task(Consumer<Task> task) {
		this.task = task;
		this.state = State.IDLE;
	}

	public static Task of(@Nonnull Runnable runnable) {
		return new Task(ignored -> runnable.run());
	}

	public static Task of(@Nonnull Consumer<Task> task) {
		return new Task(task);
	}

	public static void setPlugin(JavaPlugin plugin) {
		if (Task.plugin != null) {
			throw new UnsupportedOperationException("Plugin is already set");
		}

		Task.plugin = plugin;
	}

	public State getState() {
		return this.state;
	}

	public void cancel() {
		if (this.state != State.RUNNING) {
			throw new IllegalStateException("Task has to be running to be cancelled");
		}

		this.state = State.CANCELLED;
		this.bukkitTask.cancel();
	}

	public Task run() {
		if (this.state != State.IDLE) {
			throw new IllegalStateException("Task was already run");
		}

		this.state = State.RUNNING;
		SCHEDULER.runTask(plugin, () -> {
			this.state = State.FINISHED;
			this.task.accept(this);
		});

		return this;
	}

	public Task runAsync() {
		if (this.state != State.IDLE) {
			throw new IllegalStateException("Task was already run");
		}

		this.state = State.RUNNING;
		SCHEDULER.runTaskAsynchronously(plugin, () -> {
			this.state = State.FINISHED;
			this.task.accept(this);
		});

		return this;
	}

	public Task runLater(long delay, Unit unit) {
		if (this.state != State.IDLE) {
			throw new IllegalStateException("Task was already run");
		}

		this.state = State.RUNNING;
		this.bukkitTask = SCHEDULER.runTaskLater(plugin, () -> {
			this.state = State.FINISHED;
			this.task.accept(this);
		}, unit.convert(delay));

		return this;
	}

	public Task runLaterAsync(long delay, Unit unit) {
		if (this.state != State.IDLE) {
			throw new IllegalStateException("Task was already run");
		}

		this.state = State.RUNNING;
		this.bukkitTask = SCHEDULER.runTaskLaterAsynchronously(plugin, () -> {
			this.state = State.FINISHED;
			this.task.accept(this);
		}, unit.convert(delay));

		return this;
	}

	public Task runRepeat(long delay, long interval, Unit unit) {
		if (this.state != State.IDLE) {
			throw new IllegalStateException("Task was already run");
		}

		this.bukkitTask = SCHEDULER.runTaskTimer(plugin, () -> this.task.accept(this),
				unit.convert(delay), unit.convert(interval));
		this.state = State.RUNNING;
		return this;
	}

	public Task runRepeatAsync(long delay, long interval, Unit unit) {
		if (this.state != State.IDLE) {
			throw new IllegalStateException("Task was already run");
		}

		this.bukkitTask = SCHEDULER.runTaskTimerAsynchronously(plugin, () -> this.task.accept(this),
				unit.convert(delay), unit.convert(interval));
		this.state = State.RUNNING;
		return this;
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

	public enum State {
		IDLE,
		RUNNING,
		CANCELLED,
		FINISHED
	}
}
