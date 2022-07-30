package de.northernsi.mineplace.misc.data;

import com.google.gson.JsonObject;

public interface DataSupplier {

	<T extends DataModel> T getOrCreate(String collection, String identifier, Class<T> modelClass);

	<T extends DataModel> T getOrLoad(String collection, String identifier, Class<T> modelClass);

	JsonObject getRaw(String collection, String identifier);
}
