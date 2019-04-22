package abc.crawler;

import abc.java.JavaEntity;

public class InvalidChange<T extends JavaEntity> {

	private final T oldElement;
	private final T currentElement;
	private final String invalidChangeType;
	private final String message;

	public InvalidChange(T oldElement, T currentElement, String invalidChangeType, String message) {
		this.oldElement = oldElement;
		this.currentElement = currentElement;
		this.invalidChangeType = invalidChangeType;
		this.message = message;
	}

	public T getOldElement() {
		return oldElement;
	}

	public T getCurrentElement() {
		return currentElement;
	}

	public String getInvalidChangeType() {
		return invalidChangeType;
	}

	public String getMessage() {
		return message;
	}

}
