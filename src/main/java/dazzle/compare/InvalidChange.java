package dazzle.compare;

import dazzle.read.JavaType;

public class InvalidChange {

	private final JavaType oldType;
	private final JavaType currentType;
	private final InvalidChangeType invalidChangeType;

	public static enum InvalidChangeType {
		TYPE_REMOVED, TYPE_VISIBILITY_CHANGED, FIELD_REMOVED, FIELD_VISIBILITY_CHANGED, FIELD_TYPE_CHANGED, METHOD_REMOVED, METHOD_VISIBILITY_CHANGED, METHOD_RETURNTYPE_CHANGED, METHOD_PARAMETERTYPES_REMOVED
	}

	public InvalidChange(JavaType oldType, JavaType currentType, InvalidChangeType invalidChangeType) {
		this.oldType = oldType;
		this.currentType = currentType;
		this.invalidChangeType = invalidChangeType;
	}

	public JavaType getOldType() {
		return oldType;
	}

	public JavaType getCurrentType() {
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
