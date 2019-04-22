package abc.java;

import org.objectweb.asm.Opcodes;

public final class Access {

	public final static Access INSTANCE = new Access();

	private Access() {
		// singleton
	}

	public boolean isPublic(int access) {
		return (access & Opcodes.ACC_PUBLIC) == Opcodes.ACC_PUBLIC;
	}

	public boolean isPackagePrivate(int access) {
		return !isPublic(access) && !isProtected(access) && !isPrivate(access);
	}

	public boolean isProtected(int access) {
		return (access & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED;
	}

	public boolean isPrivate(int access) {
		return (access & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE;
	}

	public boolean isEnum(int access) {
		return (access & Opcodes.ACC_ENUM) == Opcodes.ACC_ENUM;
	}

	public boolean isInterface(int access) {
		return (access & Opcodes.ACC_INTERFACE) == Opcodes.ACC_INTERFACE;
	}

	public boolean isClass(int access) {
		return (!isEnum(access) && !(isInterface(access)));
	}

	public boolean isDeprecated(int access) {
		return (access & Opcodes.ACC_DEPRECATED) == Opcodes.ACC_DEPRECATED;
	}

	public boolean isOpcode(int access, int opCode) {
		return (access & opCode) == opCode;
	}
}
