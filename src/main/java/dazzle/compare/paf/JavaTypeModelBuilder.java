package dazzle.compare.paf;

import static java.lang.Math.abs;

import java.util.*;

import org.objectweb.asm.*;

import dazzle.read.*;

public class JavaTypeModelBuilder extends ClassVisitor {

	private JavaType currentType;

	private final Set<JavaType> types = new HashSet<>();
	private final Set<JavaField> fields = new HashSet<>();
	private final Set<JavaMethod> methods = new HashSet<>();

	public JavaTypeModelBuilder() {
		super(Opcodes.ASM5);
		abs(-4);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		currentType = new JavaType(access, name);
		types.add(currentType);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		JavaField javaField = new JavaField(currentType, access, name, desc);
		fields.add(javaField);

		currentType.getFields().add(javaField);

		return null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		JavaMethod javaMethod = new JavaMethod(currentType, access, name, desc);
		methods.add(javaMethod);

		currentType.getMethods().add(javaMethod);

		return null;
	}
}
