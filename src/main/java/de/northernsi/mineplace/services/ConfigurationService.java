package de.northernsi.mineplace.services;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import de.northernsi.mineplace.misc.data.DataModel;
import de.northernsi.mineplace.misc.data.DataSupplier;
import de.northernsi.mineplace.misc.supplier.GsonSupplier;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ConfigurationService implements DataSupplier {

	private final List<Configuration<?>> configurations = Lists.newArrayList();
	private final Path dataFolder;

	public ConfigurationService(JavaPlugin plugin) {
		this.dataFolder = plugin.getDataFolder().toPath();
	}

	@Override
	public <T extends DataModel> T getOrCreate(String collection, String identifier,
	                                           Class<T> modelClass) {
		Configuration<T> wrapper = this.getOrLoadWrapper(collection, identifier, modelClass);
		T data = wrapper.getData();
		if (data != null) {
			return data;
		}

		data = GsonSupplier.fromJson("{}", modelClass);
		wrapper = new Configuration<>(collection, identifier, data, wrapper.getPath());
		this.configurations.add(wrapper);
		return data;
	}

	@Override
	public <T extends DataModel> T getOrLoad(String collection, String identifier,
	                                         Class<T> modelClass) {
		Configuration<T> wrapper = this.getOrLoadWrapper(collection, identifier, modelClass);
		T data = wrapper.getData();
		return data;
	}

	@Override
	public JsonObject getRaw(String collection, String identifier) {
		Path resolve = this.dataFolder.resolve(collection).resolve(identifier + ".json");
		if (!Files.exists(resolve)) {
			return null;
		}

		try (BufferedReader reader = Files.newBufferedReader(resolve)) {
			return GsonSupplier.parseObject(reader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private <T extends DataModel> Configuration<T> getOrLoadWrapper(String collection,
	                                                                String identifier,
	                                                                Class<T> modelClass) {
		for (Configuration<?> configuration : this.configurations) {
			if (configuration.matches(collection, identifier)) {
				DataModel data = configuration.getData();
				if (data.getClass() == modelClass) {
					return (Configuration<T>) configuration;
				} else {
					return new Configuration<>(collection, identifier, null, configuration.getPath());
				}
			}
		}

		Path path = this.dataFolder;
		if (collection != null) {
			path = path.resolve(collection);
		}

		path = path.resolve(identifier + ".json");
		if (Files.exists(path.getParent()) && Files.exists(path)) {
			try (BufferedReader reader = Files.newBufferedReader(path)) {
				T model = GsonSupplier.fromReader(reader, modelClass);
				return new Configuration<>(collection, identifier, model, path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new Configuration<>(collection, identifier, null, path);
	}

	public static class Configuration<T extends DataModel> {

		private final String collection;
		private final String identifier;
		private final T data;
		private final Path path;

		private Configuration(String collection, String identifier, T data, Path path) {
			this.collection = collection;
			this.identifier = identifier;
			this.data = data;
			this.path = path;
		}

		@Nullable
		public String getCollection() {
			return this.collection;
		}

		@Nonnull
		public String getIdentifier() {
			return this.identifier;
		}

		@Nonnull
		public Path getPath() {
			return this.path;
		}

		@Nullable
		public T getData() {
			return this.data;
		}

		public boolean matches(@Nullable String collection, @Nonnull String identifier) {
			return Objects.equals(collection, this.collection) && identifier.equals(this.identifier);
		}
	}
}
