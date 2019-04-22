package abc.rules;

import abc.crawler.AbcRule;
import abc.crawler.InvalidChange;
import abc.java.JavaField;
import abc.java.JavaMethod;
import abc.java.JavaType;

public class TypeRemovedRule extends AbcRule {

	private static final String INVALID_CHANGE_TYPE = TypeRemovedRule.class.getSimpleName();

	@Override
	public InvalidChange<JavaType> visitType(JavaType previousVersion, int version, int access, String name,
			String signature, String superName, String[] interfaces) {
		boolean typeHasBeenRemoved = !getCurrentJarInventory().getTypes().containsKey(previousVersion.getKey());
		
		if (typeHasBeenRemoved && previousVersion.isPublic() && !previousVersion.isDeprecated()) {
			String message = String.format("%s: the public type '%s' has been removed.", INVALID_CHANGE_TYPE,
					previousVersion.getFqn());
			return new InvalidChange<JavaType>(previousVersion, null, INVALID_CHANGE_TYPE, message);
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
