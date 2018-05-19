package dazzle.matcher;

import dazzle.newcompare.InvalidChange;
import dazzle.newcompare.change.FieldRemovedInvalidChange;
import dazzle.newcompare.change.FieldVisibilityInvalidChange;
import dazzle.read.Access;
import dazzle.read.JavaField;

public class FieldMatch {

	// field's parent
	private int visibility;
	private boolean deprecated;
	// field
	private int fieldVisibility;
	private boolean fieldDeprecated;
	// compare flags
	private boolean compareFieldRemoved;
	private boolean compareFieldReducedVisibility;

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public void setFieldVisibility(int fieldVisibility) {
		this.fieldVisibility = fieldVisibility;
	}

	public void setFieldDeprecated(boolean fieldDeprecated) {
		this.fieldDeprecated = fieldDeprecated;
	}

	public void setCompareFieldRemoved(boolean compareFieldRemoved) {
		this.compareFieldRemoved = compareFieldRemoved;
	}

	public void setCompareFieldReducedVisibility(boolean compareFieldReducedVisibility) {
		this.compareFieldReducedVisibility = compareFieldReducedVisibility;
	}

	public InvalidChange<JavaField> compare(JavaField previousField, JavaField currentField) {
		// field's parent
		boolean visibilityMatches = Access.INSTANCE.isOpcode(previousField.getOwningType().getAccess(), visibility);
		if (!visibilityMatches) {
			return null;
		}

		boolean deprecatedMatches = Access.INSTANCE.isDeprecated(previousField.getOwningType().getAccess());
		if (deprecatedMatches != deprecated) {
			return null;
		}

		// !@Deprecated [public, protected]
		visibilityMatches = Access.INSTANCE.isOpcode(previousField.getAccess(), fieldVisibility);
		if (!visibilityMatches) {
			return null;
		}

		deprecatedMatches = Access.INSTANCE.isDeprecated(previousField.getAccess());
		if (deprecatedMatches != fieldDeprecated) {
			return null;
		}

		if (currentField == null) {
			if (compareFieldRemoved) {
				// violation
				return new FieldRemovedInvalidChange(previousField.getKey(), previousField, currentField);
			} else {
				return null;
			}
		}

		// previousField.getVisibility() > currentField.getVisibility()
		if (compareFieldReducedVisibility
				&& previousField.getVisibility().compareTo(currentField.getVisibility()) > 0) {
			// violation
			return new FieldVisibilityInvalidChange(previousField.getKey(), previousField, currentField);
		}

		return null;
	}

}
