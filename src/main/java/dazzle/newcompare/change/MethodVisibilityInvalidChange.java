package dazzle.newcompare.change;

import dazzle.newcompare.InvalidChange;
import dazzle.read.JavaMethod;

public class MethodVisibilityInvalidChange extends InvalidChange<JavaMethod> {

	public MethodVisibilityInvalidChange(String fqn, JavaMethod oldElement, JavaMethod currentElement) {
		super(fqn, oldElement, currentElement, InvalidChangeType.METHOD_VISIBILITY_CHANGED);
	}

	@Override
	public String toString() {
		return String.format("%s of %s: from %s to %s", getInvalidChangeType(), getFqn(),
				getOldElement().getVisibility(), getCurrentElement().getVisibility());
	}
}
