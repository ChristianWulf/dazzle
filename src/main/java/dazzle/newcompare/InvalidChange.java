package dazzle.newcompare;

public class InvalidChange<T> {

	private final T oldElement;
	private final T currentElement;
	private final InvalidChangeType invalidChangeType;
	private String fqn;

	public static enum InvalidChangeType {
		TYPE_REMOVED, TYPE_VISIBILITY_CHANGED, FIELD_REMOVED, FIELD_VISIBILITY_CHANGED, FIELD_TYPE_CHANGED, METHOD_REMOVED, METHOD_VISIBILITY_CHANGED, METHOD_RETURNTYPE_CHANGED, METHOD_PARAMETERTYPES_REMOVED
	}

	public InvalidChange(String fqn, T oldElement, T currentElement, InvalidChangeType invalidChangeType) {
		this.fqn = fqn;
		this.oldElement = oldElement;
		this.currentElement = currentElement;
		this.invalidChangeType = invalidChangeType;
	}

	public T getOldElement() {
		return oldElement;
	}

	public T getCurrentElement() {
		return currentElement;
	}

	public InvalidChangeType getInvalidChangeType() {
		return invalidChangeType;
	}

	@Override
	public String toString() {
		return String.format("%s in %s: from %s to %s", invalidChangeType, fqn, oldElement, currentElement);
	}
}
