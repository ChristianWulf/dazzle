package dazzle.visitor;

import dazzle.newcompare.InvalidChange;
import dazzle.newcompare.InvalidChange.InvalidChangeType;
import dazzle.read.Access;
import dazzle.read.JavaType;

public class TypeMatch {

	private int visibility;
	private boolean deprecated;
	private boolean compareRemoved;
	private boolean compareReducedVisibility;

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public void setCompareRemoved(boolean compareRemoved) {
		this.compareRemoved = compareRemoved;
	}

	public void setCompareReducedVisibility(boolean compareReducedVisibility) {
		this.compareReducedVisibility = compareReducedVisibility;
	}

	public InvalidChange<JavaType> compare(JavaType lastVisitedType, JavaType currentType) {
		boolean visibilityMatches = Access.INSTANCE.isOpcode(lastVisitedType.getAccess(), visibility);
		if (!visibilityMatches) {
			return null;
		}

		boolean deprecatedMatches = Access.INSTANCE.isDeprecated(lastVisitedType.getAccess());
		if (deprecatedMatches != deprecated) {
			return null;
		}

		if (compareRemoved && currentType == null) {
			// violation
			return new InvalidChange<JavaType>(lastVisitedType.getFqn(), lastVisitedType, currentType,
					InvalidChangeType.TYPE_REMOVED);
		}

		// lastVisitedType.getVisibility() > currentType.getVisibility()
		if (compareReducedVisibility && lastVisitedType.getVisibility().compareTo(currentType.getVisibility()) > 0) {
			// violation
			return new InvalidChange<JavaType>(lastVisitedType.getFqn(), lastVisitedType, currentType,
					InvalidChangeType.TYPE_VISIBILITY_CHANGED);
		}

		return null;
	}

}
