package abc.crawler;

import abc.matcher.InvalidChange;
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

	public abstract InvalidChange<JavaType> visitType(JavaType javaType, int version, int access, String name,
			String signature, String superName, String[] interfaces);

	public abstract InvalidChange<JavaField> visitField(JavaField javaField, int access, String name, String desc,
			String signature, Object value);

	public abstract InvalidChange<JavaMethod> visitMethod(JavaMethod javaMethod, int access, String name, String desc,
			String signature, String[] exceptions);

	public abstract InvalidChange<JavaType> visitInnerClass(JavaType javaType, String name, String outerName,
			String innerName, int access);

}
