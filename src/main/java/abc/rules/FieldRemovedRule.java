package abc.rules;

import abc.crawler.AbcRule;
import abc.matcher.InvalidChange;
import dazzle.matcher.Visibility;
import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;

public class FieldRemovedRule extends AbcRule {

	private static final String INVALID_CHANGE_TYPE = FieldRemovedRule.class.getSimpleName();

	@Override
	public InvalidChange<JavaType> visitType(JavaType previousVersion, int version, int access, String name,
			String signature, String superName, String[] interfaces) {
		// do nothing
		return null;
	}

	@Override
	public InvalidChange<JavaField> visitField(JavaField previousVersion, int access, String name, String desc,
			String signature, Object value) {
		// do not report method removals if the owning type has also been removed
		boolean owningTypeHasBeenRemoved = !getCurrentJarInventory().getTypes()
				.containsKey(previousVersion.getOwningType().getKey());
		if (owningTypeHasBeenRemoved) {
			return null;
		}

		boolean methodHasBeenRemoved = !getCurrentJarInventory().getFields().containsKey(previousVersion.getKey());
		if (methodHasBeenRemoved && !previousVersion.isDeprecated()) {

			if (previousVersion.getVisibility().compareTo(Visibility.PROTECTED) >= 0) {
				String message = String.format("%s: the %s field '%s' with the type '%s' has been removed.",
						INVALID_CHANGE_TYPE, previousVersion.getVisibility(), previousVersion.getFqn(),
						previousVersion.getTypeName());
				return new InvalidChange<JavaField>(previousVersion, null, INVALID_CHANGE_TYPE, message);
			}
		}
		return null;
	}

	@Override
	public InvalidChange<JavaMethod> visitMethod(JavaMethod previousVersion, int access, String name, String desc,
			String signature, String[] exceptions) {
		// do nothing
		return null;
	}

	@Override
	public InvalidChange<JavaType> visitInnerClass(JavaType javaType, String name, String outerName, String innerName,
			int access) {
		// do nothing
		return null;
	}
}
