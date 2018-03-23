package dazzle.newcompare;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;
import dazzle.read.Visitor;

public class ExtractEntityVisitor extends ClassVisitor implements Visitor {

	private final Map<String, JavaType> types = new HashMap<>();
	private final Map<String, JavaField> fields = new HashMap<>();
	private final Map<String, JavaMethod> methods = new HashMap<>();

	private JavaType currentType;

	ExtractEntityVisitor() {
		super(Opcodes.ASM5);
	}

	@Override
	public void handleClass(InputStream in) throws IOException {
		new ClassReader(in).accept(this, 0);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		currentType = new JavaType(access, name);
		types.put(currentType.getKey(), currentType);
		return;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		JavaField javaField = new JavaField(currentType, access, name, desc);
		fields.put(javaField.getKey(), javaField);
		return null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		String[] parametersAndReturnType = desc.split("[\\(\\)]");

		JavaMethod javaMethod = new JavaMethod(currentType, access, name, parametersAndReturnType[1],
				parametersAndReturnType[2]);
		methods.put(javaMethod.getKey(), javaMethod);

		return null;
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (!currentType.isPublic() || currentType.isDeprecated())
			return;

		// TODO
	}

	public Map<String, JavaType> getTypes() {
		return types;
	}

	public Map<String, JavaField> getFields() {
		return fields;
	}

	public Map<String, JavaMethod> getMethods() {
		return methods;
	}
}
