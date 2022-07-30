package de.northernsi.mineplace.misc.supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Reader;

public class GsonSupplier {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static JsonElement parse(String json) {
		return fromJson(json, JsonElement.class);
	}

	public static JsonElement parseElement(Reader reader) {
		return fromReader(reader, JsonElement.class);
	}

	public static JsonObject parseObject(String json) {
		return fromJson(json, JsonObject.class);
	}

	public static JsonObject parseObject(Reader reader) {
		return fromReader(reader, JsonObject.class);
	}

	public static JsonArray parseArray(String json) {
		return fromJson(json, JsonArray.class);
	}

	public static JsonArray parseArray(Reader reader) {
		return fromReader(reader, JsonArray.class);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return GSON.fromJson(json, clazz);
	}

	public static <T> T fromReader(Reader reader, Class<T> clazz) {
		return GSON.fromJson(reader, clazz);
	}

	public static String toJson(Object object) {
		return GSON.toJson(object);
	}
}
