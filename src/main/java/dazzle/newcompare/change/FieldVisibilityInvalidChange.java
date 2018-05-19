package dazzle.newcompare.change;

import dazzle.newcompare.InvalidChange;
import dazzle.read.JavaField;

public class FieldVisibilityInvalidChange extends InvalidChange<JavaField> {

	public FieldVisibilityInvalidChange(String fqn, JavaField oldElement, JavaField currentElement) {
		super(fqn, oldElement, currentElement, InvalidChangeType.FIELD_VISIBILITY_CHANGED);
	}

	@Override
	public String toString() {
		return String.format("%s of %s: from %s to %s", getInvalidChangeType(), getFqn(),
				getOldElement().getVisibility(), getCurrentElement().getVisibility());
	}
}
