package dazzle.visitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dazzle.newcompare.ExtractEntityVisitor;
import dazzle.newcompare.InvalidChange;
import dazzle.read.JavaEntity;
import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;
import dazzle.read.Visitor;

public class PreviousJarVisitor extends ClassVisitor implements Visitor {

	// available matches
	private final List<TypeMatch> typeMatches = new ArrayList<>();
	private final List<FieldMatch> fieldMatches = new ArrayList<>();
	private final List<MethodMatch> methodMatches = new ArrayList<>();

	// entities of the current version
	private ExtractEntityVisitor searchRepository;
	private List<InvalidChange<? extends JavaEntity>> invalidChanges = new ArrayList<>();

	private JavaType lastVisitedType;
	private boolean skipVisitingMembers;

	public PreviousJarVisitor(ExtractEntityVisitor searchRepository) {
		super(Opcodes.ASM5);
		this.searchRepository = searchRepository;
	}

	@Override
	public void handleClass(InputStream in) throws IOException {
		new ClassReader(in).accept(this, 0);
	}

	public List<InvalidChange<? extends JavaEntity>> getInvalidChanges() {
		return invalidChanges;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		skipVisitingMembers = false;
		lastVisitedType = new JavaType(access, name);

		for (TypeMatch match : typeMatches) {
			JavaType currentType = searchRepository.getTypes().get(lastVisitedType.getKey());

			InvalidChange<JavaType> invalidChange = match.compare(lastVisitedType, currentType);

			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
				skipVisitingMembers = true;
				return;
			}
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (skipVisitingMembers) return null;
		
		for (FieldMatch match : fieldMatches) {
			JavaField previousField = new JavaField(lastVisitedType, access, name, desc);
			JavaField currentField = searchRepository.getFields().get(previousField.getKey());

			InvalidChange<JavaField> invalidChange = match.compare(previousField, currentField);

			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		}

		return null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (skipVisitingMembers) return null;
		
		for (MethodMatch match : methodMatches) {
			String[] parametersAndReturnType = desc.split("[\\(\\)]");
			String parameterTypes = parametersAndReturnType[1];
			String returnTypeName = parametersAndReturnType[2];

			JavaMethod previousMethod = new JavaMethod(lastVisitedType, access, name, parameterTypes, returnTypeName);
			JavaMethod currentMethod = searchRepository.getMethods().get(previousMethod.getKey());

			InvalidChange<JavaMethod> invalidChange = match.compare(previousMethod, currentMethod);

			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		}

		return null;
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (skipVisitingMembers) return;

		// TODO Auto-generated method stub
		super.visitInnerClass(name, outerName, innerName, access);
	}

	public List<TypeMatch> getTypeMatches() {
		return typeMatches;
	}

	public List<FieldMatch> getFieldMatches() {
		return fieldMatches;
	}

	public List<MethodMatch> getMethodMatches() {
		return methodMatches;
	}
}
