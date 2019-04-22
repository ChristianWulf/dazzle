package abc.crawler;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import abc.java.JavaField;
import abc.java.JavaMethod;
import abc.java.JavaType;

public class HierarchyVisitor extends ClassVisitor {

	private final List<AbcRule> rules = new ArrayList<>();

	private JavaType lastVisitedType;

	private final List<InvalidChange<?>> invalidChanges = new ArrayList<>();

	public HierarchyVisitor() {
		super(Opcodes.ASM5);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		JavaType javaType = new JavaType(access, name);
		lastVisitedType = javaType;
		// visit the class for each visitor before going on with fields or methods
		rules.forEach(rule -> {
			InvalidChange<JavaType> invalidChange = rule.visitType(javaType, version, access, name, signature,
					superName, interfaces);
			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		});

		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		JavaField javaField = new JavaField(lastVisitedType, access, name, desc);

		rules.forEach(rule -> {
			InvalidChange<JavaField> invalidChange = rule.visitField(javaField, access, name, desc, signature, value);
			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		});

		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		JavaMethod javaMethod = new JavaMethod(lastVisitedType, access, name, desc);

		rules.forEach(rule -> {
			InvalidChange<JavaMethod> invalidChange = rule.visitMethod(javaMethod, access, name, desc, signature,
					exceptions);
			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		});

		return super.visitMethod(access, name, desc, signature, exceptions);
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		JavaType javaType = new JavaType(access, name);

		rules.forEach(rule -> {
			InvalidChange<JavaType> invalidChange = rule.visitInnerClass(javaType, name, outerName, innerName, access);
			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		});

		super.visitInnerClass(name, outerName, innerName, access);
	}

	public void addClassVisitor(AbcRule classVisitor) {
		this.rules.add(classVisitor);
	}

	public List<InvalidChange<?>> getInvalidChanges() {
		return invalidChanges;
	}
	
}
