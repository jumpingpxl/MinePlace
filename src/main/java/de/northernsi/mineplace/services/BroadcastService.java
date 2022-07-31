package de.northernsi.mineplace.services;

import com.google.common.collect.Lists;
import de.northernsi.mineplace.misc.Task;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BroadcastService {

	private final List<BaseComponent[]> broadcastMessages;
	private final JavaPlugin javaPlugin;
	private int lastBroadcastIndex;

	private Task task;

	public BroadcastService(JavaPlugin javaPlugin) {
		this.javaPlugin = javaPlugin;

		this.broadcastMessages = Lists.newArrayList();
		this.setupTask(10, Task.Unit.MINUTES);
	}

	public void setupTask(int interval, Task.Unit intervalUnit) {
		if (this.task != null) {
			this.task.cancel();
		}

		this.task = Task.of(this::broadcast).runRepeat(interval, interval, intervalUnit);
	}

	public void broadcast() {
		if (this.broadcastMessages.isEmpty()) {
			return;
		}

		if (++this.lastBroadcastIndex >= this.broadcastMessages.size()) {
			this.lastBroadcastIndex = 0;
		}

		BaseComponent[] component = this.broadcastMessages.get(this.lastBroadcastIndex);
		this.javaPlugin.getServer().spigot().broadcast(component);
	}

	public void addMessage(BaseComponent[] component) {
		this.broadcastMessages.add(component);
	}
}
