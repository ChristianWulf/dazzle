package dazzle.compare;

import java.io.*;
import java.util.*;

import org.objectweb.asm.*;

import dazzle.read.*;

class AllVisitor extends ClassVisitor implements Visitor {

	private static final JavaType DEFAULT_TYPE = new JavaType(0, "/");

	private final List<JavaType> types = new ArrayList<>();
	private final List<JavaField> fields = new ArrayList<>();
	private final List<JavaMethod> methods = new ArrayList<>();

	private JavaType currentType;

	AllVisitor() {
		super(Opcodes.ASM5);
	}

	@Override
	public void handleClass(InputStream in) throws IOException {
		new ClassReader(in).accept(this, 0);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if ((access & Opcodes.ACC_PUBLIC) != Opcodes.ACC_PUBLIC) {
			currentType = DEFAULT_TYPE;
			return;
		}
		if ((access & Opcodes.ACC_DEPRECATED) == Opcodes.ACC_DEPRECATED) {
			currentType = DEFAULT_TYPE;
			return;
		}
		currentType = new JavaType(access, name);
		types.add(currentType);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (!currentType.isPublic() || currentType.isDeprecated()) return null;

		JavaField javaField = new JavaField(currentType, access, name, desc);
		fields.add(javaField);

		return null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (!currentType.isPublic() || currentType.isDeprecated()) return null;

		String[] parametersAndReturnType = desc.split("\\)");
		
		JavaMethod javaMethod = new JavaMethod(currentType, access, name, parametersAndReturnType[0], parametersAndReturnType[1]);
		methods.add(javaMethod);

		return null;
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (!currentType.isPublic() || currentType.isDeprecated()) return;

		// TODO
	}

	public List<JavaType> getTypes() {
		return types;
	}

	public List<JavaField> getFields() {
		return fields;
	}

	public List<JavaMethod> getMethods() {
		return methods;
	}
}
