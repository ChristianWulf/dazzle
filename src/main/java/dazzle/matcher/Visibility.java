package dazzle.matcher;

import org.objectweb.asm.Opcodes;

public enum Visibility {

	PRIVATE, PACKAGE_PRIVATE, PROTECTED, PUBLIC;

	public static Visibility of(int access) {
		int filteredAccess = access & 0x0007;
		switch (filteredAccess) {
		case 0x0000:
			return Visibility.PACKAGE_PRIVATE;
		case Opcodes.ACC_PUBLIC:
			return Visibility.PUBLIC;
		case Opcodes.ACC_PRIVATE:
			return Visibility.PRIVATE;
		case Opcodes.ACC_PROTECTED:
			return Visibility.PROTECTED;
		default:
			throw new IllegalStateException("Illegal visibility value: " + access + ".");
		}
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
