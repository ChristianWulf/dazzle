package abc.crawler;

public class AbcClassLoader<T> {

	@SuppressWarnings("unchecked")
	public T load(String fullyQualifiedClassName) {
		ClassLoader classLoader = this.getClass().getClassLoader();

		Class<T> clazz;
		try {
			clazz = (Class<T>) classLoader.loadClass(fullyQualifiedClassName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(fullyQualifiedClassName);
		}
		
		// TODO asSubclass?

		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("Invalid rule: " + fullyQualifiedClassName);
		}
	}

}
