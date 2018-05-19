package dazzle.visitor;

import dazzle.newcompare.InvalidChange;
import dazzle.newcompare.change.MethodRemovedInvalidChange;
import dazzle.newcompare.change.MethodVisibilityInvalidChange;
import dazzle.read.Access;
import dazzle.read.JavaMethod;

public class MethodMatch {

	// method's parent
	private int visibility;
	private boolean deprecated;
	// method
	private boolean methodDeprecated;
	private int methodVisibility;
	// compare flags
	private boolean compareMethodReducedVisibility;
	private boolean compareMethodRemoved;

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	public void setCompareMethodReducedVisibility(boolean compareMethodReducedVisibility) {
		this.compareMethodReducedVisibility = compareMethodReducedVisibility;
	}

	public void setCompareMethodRemoved(boolean compareMethodRemoved) {
		this.compareMethodRemoved = compareMethodRemoved;
	}

	public void setMethodDeprecated(boolean methodDeprecated) {
		this.methodDeprecated = methodDeprecated;
	}

	public void setMethodVisibility(int methodVisibility) {
		this.methodVisibility = methodVisibility;
	}

	public InvalidChange<JavaMethod> compare(JavaMethod lastVisitedMethod, JavaMethod currentMethod) {
		// method's parent
		boolean visibilityMatches = Access.INSTANCE.isOpcode(lastVisitedMethod.getOwningType().getAccess(), visibility);
		if (!visibilityMatches) {
			return null;
		}

		boolean deprecatedMatches = Access.INSTANCE.isDeprecated(lastVisitedMethod.getOwningType().getAccess());
		if (deprecatedMatches != deprecated) {
			return null;
		}

		// method
		visibilityMatches = Access.INSTANCE.isOpcode(lastVisitedMethod.getAccess(), methodVisibility);
		if (!visibilityMatches) {
			return null;
		}

		deprecatedMatches = Access.INSTANCE.isDeprecated(lastVisitedMethod.getAccess());
		if (deprecatedMatches != methodDeprecated) {
			return null;
		}

		if (currentMethod == null) {
			if (compareMethodRemoved) {
				// violation
				return new MethodRemovedInvalidChange(lastVisitedMethod.getKey(), lastVisitedMethod, currentMethod);
			} else {
				return null;
			}
		}

		// lastVisitedType.getVisibility() > currentType.getVisibility()
		if (compareMethodReducedVisibility
				&& lastVisitedMethod.getVisibility().compareTo(currentMethod.getVisibility()) > 0) {
			// violation
			return new MethodVisibilityInvalidChange(lastVisitedMethod.getKey(), lastVisitedMethod, currentMethod);
		}

		return null;
	}

}
