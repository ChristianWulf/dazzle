package dazzle.read;

import dazzle.visitor.Visibility;

public class JavaMethod implements JavaEntity {

	private final JavaType owningType;
	private final int access;
	private final String name;
	private final String parameterTypes;
	private final String returnTypeName;
	private final Visibility visibility;

	public JavaMethod(JavaType owningType, int access, String name, String parameterTypes, String returnTypeName) {
		this.owningType = owningType;
		this.access = access;
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.returnTypeName = returnTypeName;
		this.visibility = Visibility.of(access);
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

	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public String getKey() {
		return String.format("%s.%s(%s)", owningType.getFqn(), name, parameterTypes);
	}

	public boolean isPublic() {
		return Access.INSTANCE.isPublic(access);
	}

	public boolean isProtected() {
		return Access.INSTANCE.isProtected(access);
	}

	@Override
	public String toString() {
		return String.format("%s %s %s(%s)", visibility, returnTypeName, name, parameterTypes);
	}

}
