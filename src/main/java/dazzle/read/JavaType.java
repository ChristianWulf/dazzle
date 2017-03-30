package dazzle.read;

import org.objectweb.asm.Opcodes;

public class JavaType implements JavaEntity {

	private final int access;
	private final String fqn;
	private final String packageName;

	public JavaType(int access, String name) {
		this.access = access;
		this.fqn = name;
		int endIndex = name.lastIndexOf('/');
		this.packageName = name.substring(0, endIndex);
	}

	public int getAccess() {
		return access;
	}

	public String getFqn() {
		return fqn;
	}

	public String getPackageName() {
		return packageName;
	}

	public boolean isPublic() {
		return (access & Opcodes.ACC_PUBLIC) == Opcodes.ACC_PUBLIC;
	}

	public boolean isPackagePrivate() {
		return !isPublic() && !isProtected() && !isPrivate();
	}

	private boolean isProtected() {
		return (access & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED;
	}

	private boolean isPrivate() {
		return (access & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE;
	}

	public boolean isEnum() {
		return (access & Opcodes.ACC_ENUM) == Opcodes.ACC_ENUM;
	}

	public boolean isInterface() {
		return (access & Opcodes.ACC_INTERFACE) == Opcodes.ACC_INTERFACE;
	}

	public boolean isClass() {
		return (!isEnum() && !(isInterface()));
	}

	public boolean isDeprecated() {
		return (access & Opcodes.ACC_DEPRECATED) == Opcodes.ACC_DEPRECATED;
	}

	@Override
	public String getKey() {
		return fqn;
	}

	@Override
	public String toString() {
		String visibility = (isPublic()) ? "public" : ((isPrivate()) ? "private" : "");
		String kind = (isEnum()) ? "enum" : ((isInterface()) ? "interface" : "class");
		return String.format("%s %s %s", visibility, kind, fqn);
	}

}
