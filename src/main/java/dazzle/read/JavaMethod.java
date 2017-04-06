package dazzle.read;

public class JavaMethod implements JavaEntity {

	private final JavaType owningType;
	private final int access;
	private final String name;
	private final String returnTypeName;

	public JavaMethod(JavaType owningType, int access, String name, String desc) {
		this.owningType = owningType;
		this.access = access;
		this.name = name;
		this.returnTypeName = desc;
	}

	public JavaType getOwningType() {
		return owningType;
	}

	public int getAccess() {
		return access;
	}

	public String getName() {
		return name;
	}

	public String getReturnTypeName() {
		return returnTypeName;
	}

	@Override
	public String getKey() {
		return String.format("%s.%s", owningType, name);
	}

	public boolean isPublic() {
		return Access.INSTANCE.isPublic(access);
	}

	private boolean isProtected() {
		return Access.INSTANCE.isProtected(access);
	}

	private boolean isPrivate() {
		return Access.INSTANCE.isPrivate(access);
	}

	@Override
	public String toString() {
		String visibility = (isPublic()) ? "public"
				: (((isPrivate()) ? "private" : (isProtected()) ? "protected" : ""));
		return String.format("%s %s %s.%s", visibility, returnTypeName, owningType.getFqn(), name);
	}

}
