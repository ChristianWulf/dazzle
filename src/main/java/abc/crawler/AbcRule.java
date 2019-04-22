package abc.crawler;

import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;

public abstract class AbcRule {

	private CurrentJarInventory currentJarInventory;

	public void setCurrentJarInventory(CurrentJarInventory currentJarInventory) {
		this.currentJarInventory = currentJarInventory;
	}

	protected CurrentJarInventory getCurrentJarInventory() {
		return currentJarInventory;
	}

	public abstract InvalidChange<JavaType> visitType(JavaType previousVersion, int version, int access, String name,
			String signature, String superName, String[] interfaces);

	public abstract InvalidChange<JavaField> visitField(JavaField previousVersion, int access, String name, String desc,
			String signature, Object value);

	public abstract InvalidChange<JavaMethod> visitMethod(JavaMethod previousVersion, int access, String name, String desc,
			String signature, String[] exceptions);

	public abstract InvalidChange<JavaType> visitInnerClass(JavaType previousVersion, String name, String outerName,
			String innerName, int access);

}
