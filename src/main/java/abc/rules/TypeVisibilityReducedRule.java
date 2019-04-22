package abc.rules;

import abc.crawler.AbcRule;
import abc.crawler.InvalidChange;
import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;

public class TypeVisibilityReducedRule extends AbcRule {

	private static final String INVALID_CHANGE_TYPE = TypeVisibilityReducedRule.class.getSimpleName();

	@Override
	public InvalidChange<JavaType> visitType(JavaType previousVersion, int version, int access, String name,
			String signature, String superName, String[] interfaces) {
		boolean typeIsStillAvailable = getCurrentJarInventory().getTypes().containsKey(previousVersion.getKey());

		if (typeIsStillAvailable && !previousVersion.isDeprecated()) {
			JavaType currentJavaType = getCurrentJarInventory().getTypes().get(previousVersion.getKey());
			boolean isReduced = currentJavaType.getVisibility().compareTo(previousVersion.getVisibility()) < 0;

			if (isReduced) {
				String message = String.format(
						"%s: the visibility of the type '%s' has been reduced from '%s' to '%s'.", INVALID_CHANGE_TYPE,
						previousVersion.getFqn(), previousVersion.getVisibility(), currentJavaType.getVisibility());
				return new InvalidChange<JavaType>(previousVersion, currentJavaType, INVALID_CHANGE_TYPE, message);
			}
		}
		return null;
	}

	@Override
	public InvalidChange<JavaField> visitField(JavaField javaField, int access, String name, String desc,
			String signature, Object value) {
		// do nothing
		return null;
	}

	@Override
	public InvalidChange<JavaMethod> visitMethod(JavaMethod javaMethod, int access, String name, String desc,
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
