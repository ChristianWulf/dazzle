package abc.rules;

import abc.crawler.AbcRule;
import abc.crawler.InvalidChange;
import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;
import dazzle.read.Visibility;

public class MethodRemovedRule extends AbcRule {

	private static final String INVALID_CHANGE_TYPE = MethodRemovedRule.class.getSimpleName();

	@Override
	public InvalidChange<JavaType> visitType(JavaType previousVersion, int version, int access, String name,
			String signature, String superName, String[] interfaces) {
		// do nothing
		return null;
	}

	@Override
	public InvalidChange<JavaField> visitField(JavaField javaField, int access, String name, String desc,
			String signature, Object value) {
		// do nothing
		return null;
	}

	@Override
	public InvalidChange<JavaMethod> visitMethod(JavaMethod previousVersion, int access, String name, String desc,
			String signature, String[] exceptions) {
		// do not report method removals if the owning type has also been removed
		boolean owningTypeHasBeenRemoved = !getCurrentJarInventory().getTypes()
				.containsKey(previousVersion.getOwningType().getKey());
		if (owningTypeHasBeenRemoved) {
			return null;
		}

		boolean methodHasBeenRemoved = !getCurrentJarInventory().getMethods().containsKey(previousVersion.getKey());
		if (methodHasBeenRemoved && !previousVersion.isDeprecated()) {

			if (previousVersion.getVisibility().compareTo(Visibility.PROTECTED) >= 0) {
				String message = String.format("%s: the %s method '%s' with the return type '%s' has been removed.",
						INVALID_CHANGE_TYPE, previousVersion.getVisibility(), previousVersion.getFqn(),
						previousVersion.getReturnTypeName());
				return new InvalidChange<JavaMethod>(previousVersion, null, INVALID_CHANGE_TYPE, message);
			}
		}
		return null;
	}

	@Override
	public InvalidChange<JavaType> visitInnerClass(JavaType javaType, String name, String outerName, String innerName,
			int access) {
		// do nothing
		return null;
	}
}
