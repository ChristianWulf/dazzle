package dazzle.read;

import dazzle.visitor.Visibility;

public class JavaType implements JavaEntity {

	private final int access;
	private final String fqn;
	private final String packageName;
	private final Visibility visibility;

	public JavaType(int access, String name) {
		this.access = access;
		this.fqn = name;
		int endIndex = name.lastIndexOf('/');
		this.packageName = (endIndex != -1) ? name.substring(0, endIndex) : "";
		this.visibility = Visibility.of(access);
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

	public Visibility getVisibility() {
		return visibility;
	}

	public boolean isPublic() {
		return Access.INSTANCE.isPublic(access);
	}

	public boolean isPackagePrivate() {
		return Access.INSTANCE.isPackagePrivate(access);
	}

	public boolean isProtected() {
		return Access.INSTANCE.isProtected(access);
	}

	public boolean isPrivate() {
		return Access.INSTANCE.isPrivate(access);
	}

	public boolean isEnum() {
		return Access.INSTANCE.isEnum(access);
	}

	public boolean isInterface() {
		return Access.INSTANCE.isInterface(access);
	}

	public boolean isClass() {
		return Access.INSTANCE.isClass(access);
	}

	public boolean isDeprecated() {
		return Access.INSTANCE.isDeprecated(access);
	}

	@Override
	public String getKey() {
		return fqn;
	}

	@Override
	public String toString() {
//		String visibility = (isPublic()) ? "public" : ((isPrivate()) ? "private" : "");
		String kind = getKind();
		return String.format("%s %s %s", visibility, kind, fqn);
	}

	public String getKind() {
		return (isEnum()) ? "enum" : ((isInterface()) ? "interface" : "class");
	}

}
