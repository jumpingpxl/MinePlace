package de.northernsi.mineplace.misc.supplier;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CooldownSupplier {

	private static final Map<Type, CooldownSupplier> COOLDOWN_SUPPLIERS = Maps.newHashMap();

	private final Type type;
	private final List<CooldownEntry> entries;

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
		Optional<CooldownEntry> entry = this.getEntry(uniqueId);
		if (!entry.isPresent()) {
			return false;
		}

		CooldownEntry cooldownEntry = entry.get();
		long cooldown = cooldownEntry.getTimestamp() + this.type.getCooldown();
		boolean cooldownExpired = cooldown < System.currentTimeMillis();
		if (cooldownExpired) {
			this.entries.remove(cooldownEntry);
		}

		return !cooldownExpired;
	}

	public void add(Player player) {
		this.add(player.getUniqueId());
	}

	public void add(UUID uniqueId) {
		this.entries.add(new CooldownEntry(uniqueId, System.currentTimeMillis()));
	}

	public boolean remove(Player player) {
		return this.remove(player.getUniqueId());
	}

	public boolean remove(UUID uniqueId) {
		Optional<CooldownEntry> entry = this.getEntry(uniqueId);
		return entry.map(this.entries::remove).orElse(false);
	}

	private Optional<CooldownEntry> getEntry(UUID uniqueId) {
		for (CooldownEntry entry : this.entries) {
			if (entry.getUniqueId().equals(uniqueId)) {
				return Optional.of(entry);
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

	private static class CooldownEntry {

		private final UUID uniqueId;
		private final long timestamp;

		public CooldownEntry(UUID uniqueId, long timestamp) {
			this.uniqueId = uniqueId;
			this.timestamp = timestamp;
		}

		public UUID getUniqueId() {
			return this.uniqueId;
		}

		public long getTimestamp() {
			return this.timestamp;
		}
	}
}
