package dazzle.newcompare.change;

import dazzle.newcompare.InvalidChange;
import dazzle.read.JavaType;

public class TypeVisibilityInvalidChange extends InvalidChange<JavaType> {

	public TypeVisibilityInvalidChange(String fqn, JavaType oldElement, JavaType currentElement) {
		super(fqn, oldElement, currentElement, InvalidChangeType.TYPE_VISIBILITY_CHANGED);
	}

	@Override
	public String toString() {
		return String.format("%s in %s: from %s to %s", getInvalidChangeType(), getFqn(),
				getOldElement().getVisibility(), getCurrentElement().getVisibility());
	}
}
