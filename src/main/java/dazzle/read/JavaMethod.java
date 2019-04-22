package dazzle.read;

import dazzle.matcher.Visibility;

public class JavaMethod implements JavaEntity {

	private final JavaType owningType;
	private final int access;
	private final String fqn;
	private final String name;
	private final String parameterTypes;
	private final String returnTypeName;
	private final Visibility visibility;

	@Deprecated
	public JavaMethod(JavaType owningType, int access, String name, String parameterTypes, String returnTypeName) {
		this.owningType = owningType;
		this.access = access;
		this.fqn = name.replaceAll("/", ".");
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.returnTypeName = returnTypeName;
		this.visibility = Visibility.of(access);
	}

	public JavaMethod(JavaType owningType, int access, String name, String desc) {
		String[] parametersAndReturnType = desc.split("[\\(\\)]");
		String parameterTypes = parametersAndReturnType[1];
		String returnTypeName = parametersAndReturnType[2];

		this.owningType = owningType;
		this.access = access;
		this.fqn = String.format("%s.%s(%s)", owningType.getFqn(), name, parameterTypes);
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

	public String getFqn() {
		return fqn;
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

	public boolean isDeprecated() {
		return Access.INSTANCE.isDeprecated(access);
	}

	@Override
	public String toString() {
		return String.format("%s %s %s(%s)", visibility, returnTypeName, name, parameterTypes);
	}

}
