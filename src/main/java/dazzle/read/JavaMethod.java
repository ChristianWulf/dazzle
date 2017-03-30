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
		return name;
	}

}
