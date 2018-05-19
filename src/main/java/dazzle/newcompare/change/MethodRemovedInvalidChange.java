package dazzle.newcompare.change;

import dazzle.newcompare.InvalidChange;
import dazzle.read.JavaMethod;

public class MethodRemovedInvalidChange extends InvalidChange<JavaMethod> {

	public MethodRemovedInvalidChange(String fqn, JavaMethod oldElement, JavaMethod currentElement) {
		super(fqn, oldElement, currentElement, InvalidChangeType.METHOD_REMOVED);
	}

	@Override
	public String toString() {
		return String.format("%s in %s: %s", getInvalidChangeType(), getOldElement().getOwningType().getFqn(),
				getOldElement());
	}
}
