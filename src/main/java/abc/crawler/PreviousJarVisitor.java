package abc.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import dazzle.matcher.FieldMatch;
import dazzle.matcher.MethodMatch;
import dazzle.matcher.TypeMatch;
import dazzle.newcompare.InvalidChange;
import dazzle.newcompare.PackageNameExcludeSet;
import dazzle.newcompare.PackageNameIncludeSet;
import dazzle.read.JavaEntity;
import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;
import dazzle.read.Visitor;

public class PreviousJarVisitor extends ClassVisitor implements Visitor {

	private PackageNameIncludeSet packageNamesToInclude;
	private PackageNameExcludeSet packageNamesToExclude;

	// available matches
	private final List<TypeMatch> typeMatches = new ArrayList<>();
	private final List<FieldMatch> fieldMatches = new ArrayList<>();
	private final List<MethodMatch> methodMatches = new ArrayList<>();

	// entities of the current version
	private CurrentJarInventory currentJarInventory;
	private List<InvalidChange<? extends JavaEntity>> invalidChanges = new ArrayList<>();

	private JavaType lastVisitedType;
	private boolean skipVisitingMembers;

	public PreviousJarVisitor(CurrentJarInventory currentJarInventory) {
		super(Opcodes.ASM5);
		this.currentJarInventory = currentJarInventory;
	}

	public void setPackageNamesToInclude(PackageNameIncludeSet packageNamesToInclude) {
		this.packageNamesToInclude = packageNamesToInclude;
	}

	public void setPackageNamesToExclude(PackageNameExcludeSet packageNamesToExclude) {
		this.packageNamesToExclude = packageNamesToExclude;
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

		if (!packageNamesToInclude.contains(lastVisitedType.getPackageName())) {
			return;
		} else if (packageNamesToExclude.contains(lastVisitedType.getPackageName())) {
			return;
		}

		for (TypeMatch match : typeMatches) {
			JavaType currentType = currentJarInventory.getTypes().get(lastVisitedType.getKey());

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
		if (skipVisitingMembers)
			return null;

		if (!packageNamesToInclude.contains(lastVisitedType.getPackageName())) {
			return null;
		} else if (packageNamesToExclude.contains(lastVisitedType.getPackageName())) {
			return null;
		}

		for (FieldMatch match : fieldMatches) {
			JavaField previousField = new JavaField(lastVisitedType, access, name, desc);
			JavaField currentField = currentJarInventory.getFields().get(previousField.getKey());

			InvalidChange<JavaField> invalidChange = match.compare(previousField, currentField);

			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		}

		return null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (skipVisitingMembers)
			return null;

		if (!packageNamesToInclude.contains(lastVisitedType.getPackageName())) {
			return null;
		} else if (packageNamesToExclude.contains(lastVisitedType.getPackageName())) {
			return null;
		}

		for (MethodMatch match : methodMatches) {
			String[] parametersAndReturnType = desc.split("[\\(\\)]");
			String parameterTypes = parametersAndReturnType[1];
			String returnTypeName = parametersAndReturnType[2];

			JavaMethod previousMethod = new JavaMethod(lastVisitedType, access, name, parameterTypes, returnTypeName);
			JavaMethod currentMethod = currentJarInventory.getMethods().get(previousMethod.getKey());

			InvalidChange<JavaMethod> invalidChange = match.compare(previousMethod, currentMethod);

			if (invalidChange != null) {
				invalidChanges.add(invalidChange);
			}
		}

		return null;
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (skipVisitingMembers)
			return;

		if (!packageNamesToInclude.contains(lastVisitedType.getPackageName())) {
			return;
		}

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
