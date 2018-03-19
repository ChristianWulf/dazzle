package dazzle.read;

import dazzle.visitor.Visibility;

public class JavaField implements JavaEntity {

	private final JavaType owningType;
	private final int access;
	private final String name;
	private final String typeName;
	private final Visibility visibility;

	public JavaField(JavaType owningType, int access, String name, String desc) {
		if (null == owningType) { throw new IllegalArgumentException("owningType is null"); }
		if (null == name) { throw new IllegalArgumentException("name is null"); }
		if (null == desc) { throw new IllegalArgumentException("desc is null"); }
		this.owningType = owningType;
		this.access = access;
		this.name = name;
		this.typeName = desc;
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

	public String getTypeName() {
		return typeName;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public String getKey() {
		return name;
	}

	
	public boolean isPublic() {
		return Access.INSTANCE.isPublic(access);
	}

	public boolean isProtected() {
		return Access.INSTANCE.isProtected(access);
	}

	public boolean isPrivate() {
		return Access.INSTANCE.isPrivate(access);
	}

	@Override
	public String toString() {
		String visibility = (isPublic()) ? "public" : (((isPrivate()) ? "private" : (isProtected()) ? "protected" : ""));
		return String.format("%s %s %s", visibility, typeName, name);
	}

}
