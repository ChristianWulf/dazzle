package dazzle.visitor;

import dazzle.newcompare.InvalidChange;
import dazzle.read.Access;
import dazzle.read.JavaField;

public class FieldMatch {

	private int visibility;

	public InvalidChange<JavaField> compare(JavaField previousField, JavaField currentField) {
		// !@Deprecated
		// [public,
		// protected]
		if (Access.INSTANCE.isDeprecated(previousField.getAccess())) {
			return null;
		}

		if (!Access.INSTANCE.isOpcode(previousField.getAccess(), visibility)) {
			return null;
		}

		if (currentField == null) {
			// TODO
			return null;
		}

		// previousField.getVisibility() > currentField.getVisibility()
		if (previousField.getVisibility().compareTo(currentField.getVisibility()) > 0) {
			// violation
		}

		// TODO Auto-generated method stub
		return null;
	}

}
