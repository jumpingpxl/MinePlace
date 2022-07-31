package de.northernsi.mineplace.misc.supplier;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.northernsi.mineplace.misc.Pair;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CooldownSupplier {

	private static final Map<Type, CooldownSupplier> COOLDOWN_SUPPLIERS = Maps.newHashMap();

	private final Type type;
	private final List<Pair<UUID, Long>> entries;

	private CooldownSupplier(Type type) {
		this.type = type;
		this.entries = Lists.newArrayList();
	}

	public static CooldownSupplier of(Type type) {
		return COOLDOWN_SUPPLIERS.computeIfAbsent(type, CooldownSupplier::new);
	}

	public boolean has(Player player) {
		return this.has(player.getUniqueId());
	}

	public boolean has(UUID uniqueId) {
		return this.remainingTime(uniqueId) > 0;
	}

	public void add(Player player) {
		this.add(player.getUniqueId());
	}

	public void add(UUID uniqueId) {
		this.entries.add(Pair.of(uniqueId, System.currentTimeMillis()));
	}

	public boolean remove(Player player) {
		return this.remove(player.getUniqueId());
	}

	public boolean remove(UUID uniqueId) {
		Optional<Pair<UUID, Long>> entry = this.getEntry(uniqueId);
		return entry.map(this.entries::remove).orElse(false);
	}

	public long remainingTime(Player player) {
		return this.remainingTime(player.getUniqueId());
	}

	public long remainingTime(UUID uniqueId) {
		Optional<Pair<UUID, Long>> entry = this.getEntry(uniqueId);
		if (!entry.isPresent()) {
			return 0L;
		}

		Pair<UUID, Long> pair = entry.get();
		long timestamp = pair.getSecond() + this.type.getCooldown();
		long remainingTime = Math.max(0, timestamp - System.currentTimeMillis());
		if (remainingTime == 0L) {
			this.entries.remove(pair);
		}

		return remainingTime;
	}

	private Optional<Pair<UUID, Long>> getEntry(UUID uniqueId) {
		for (Pair<UUID, Long> pair : this.entries) {
			if (pair.getFirst().equals(uniqueId)) {
				return Optional.of(pair);
			}
		}

		return Optional.empty();
	}

	public enum Type {
		CHAT(800),
		PLACE(5000);

		private static final Type[] VALUES = values();
		private final long cooldown;

		Type(long cooldown) {
			this.cooldown = cooldown;
		}

		public static Type[] getValues() {
			return VALUES;
		}

		public long getCooldown() {
			return this.cooldown;
		}
	}
}
