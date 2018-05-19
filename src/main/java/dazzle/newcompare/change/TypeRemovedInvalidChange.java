package dazzle.newcompare.change;

import dazzle.newcompare.InvalidChange;
import dazzle.read.JavaType;

public class TypeRemovedInvalidChange extends InvalidChange<JavaType> {

	public TypeRemovedInvalidChange(String fqn, JavaType oldElement, JavaType currentElement) {
		super(fqn, oldElement, currentElement, InvalidChangeType.TYPE_REMOVED);
	}

	@Override
	public String toString() {
		return String.format("%s: %s", getInvalidChangeType(), getOldElement());
	}
}
