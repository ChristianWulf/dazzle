package dazzle.compare;

public class InvalidChange<T> {

	private final T oldType;
	private final T currentType;
	private final InvalidChangeType invalidChangeType;

	public static enum InvalidChangeType {
		TYPE_REMOVED, TYPE_VISIBILITY_CHANGED, FIELD_REMOVED, FIELD_VISIBILITY_CHANGED, FIELD_TYPE_CHANGED, METHOD_REMOVED, METHOD_VISIBILITY_CHANGED, METHOD_RETURNTYPE_CHANGED, METHOD_PARAMETERTYPES_REMOVED
	}

	public InvalidChange(T oldElement, T currentElement, InvalidChangeType invalidChangeType) {
		this.oldType = oldElement;
		this.currentType = currentElement;
		this.invalidChangeType = invalidChangeType;
	}

	public T getOldElement() {
		return oldType;
	}

	public T getCurrentElement() {
		return currentType;
	}

	public InvalidChangeType getInvalidChangeType() {
		return invalidChangeType;
	}

	@Override
	public String toString() {
		return String.format("%s: from %s to %s", invalidChangeType, oldType, currentType);
	}
}
