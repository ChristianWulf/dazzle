package dazzle.newcompare.change;

import dazzle.newcompare.InvalidChange;
import dazzle.read.JavaField;

public class FieldRemovedInvalidChange extends InvalidChange<JavaField> {

	public FieldRemovedInvalidChange(String fqn, JavaField oldElement, JavaField currentElement) {
		super(fqn, oldElement, currentElement, InvalidChangeType.FIELD_REMOVED);
	}

	@Override
	public String toString() {
		return String.format("%s in %s: %s", getInvalidChangeType(), getOldElement().getOwningType().getFqn(),
				getOldElement());
	}
}
