package dazzle.read;

public class JavaField implements JavaEntity {

	private final JavaType owningType;
	private final int access;
	private final String name;
	private final String typeName;

	public JavaField(JavaType owningType, int access, String name, String desc) {
		this.owningType = owningType;
		this.access = access;
		this.name = name;
		this.typeName = desc;
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

	@Override
	public String getKey() {
		return name;
	}

}
