package dazzle.read;

import dazzle.matcher.Visibility;

public class JavaField implements JavaEntity {

	private final JavaType owningType;
	private final int access;
	private final String fqn;
	private final String name;
	private final String typeName;
	private final Visibility visibility;

	public JavaField(JavaType owningType, int access, String name, String desc) {
		if (null == owningType) {
			throw new IllegalArgumentException("owningType is null");
		}
		if (null == name) {
			throw new IllegalArgumentException("name is null");
		}
		if (null == desc) {
			throw new IllegalArgumentException("desc is null");
		}
		this.owningType = owningType;
		this.access = access;
		this.fqn = String.format("%s.%s", owningType.getFqn(), name);
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

	public String getFqn() {
		return fqn;
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
		return String.format("%s.%s", owningType.getFqn(), name);
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

	public boolean isDeprecated() {
		return Access.INSTANCE.isDeprecated(access);
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", visibility, typeName, name);
	}

}
