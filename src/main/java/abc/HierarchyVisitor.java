package abc;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class HierarchyVisitor extends ClassVisitor {

	private List<ClassVisitor> classVisitors = new ArrayList<>();

	public HierarchyVisitor() {
		super(Opcodes.ASM5);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// visit the class for each visitor before going on with fields or methods
		classVisitors.forEach(rule -> {
			rule.visit(version, access, name, signature, superName, interfaces);
		});

		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		classVisitors.forEach(rule -> {
			rule.visitField(access, name, desc, signature, value);
		});

		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		classVisitors.forEach(rule -> {
			rule.visitMethod(access, name, desc, signature, exceptions);
		});

		return super.visitMethod(access, name, desc, signature, exceptions);
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		classVisitors.forEach(rule -> {
			rule.visitInnerClass(name, outerName, innerName, access);
		});

		super.visitInnerClass(name, outerName, innerName, access);
	}

	public void addClassVisitor(ClassVisitor classVisitor) {
		this.classVisitors.add(classVisitor);
	}

}
