package de.northernsi.mineplace.misc.data;

public class DataHolder<T extends DataModel> {

	private final DataSupplier dataSupplier;
	private final String collection;
	private final Class<T> modelClass;

	protected DataHolder(DataSupplier dataSupplier, String collection, Class<T> modelClass) {
		this.dataSupplier = dataSupplier;
		this.collection = collection;
		this.modelClass = modelClass;
	}

	protected T getOrCreateData(Object identifier) {
		return this.dataSupplier.getOrCreate(this.collection, identifier.toString(), this.modelClass);
	}

	protected T getData(Object identifier) {
		return this.dataSupplier.getOrLoad(this.collection, identifier.toString(), this.modelClass);
	}
}
