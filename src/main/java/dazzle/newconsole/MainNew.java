package dazzle.newconsole;

import java.io.IOException;
import java.util.List;

import org.objectweb.asm.Opcodes;

import com.beust.jcommander.JCommander;

import dazzle.matcher.FieldMatch;
import dazzle.matcher.MethodMatch;
import dazzle.matcher.TypeMatch;
import dazzle.newcompare.InvalidChange;
import dazzle.newcompare.InvalidChangeDetector;
import dazzle.newcompare.PackageNameExcludeSet;
import dazzle.newcompare.PackageNameIncludeSet;
import dazzle.read.JavaEntity;

public class MainNew {

	public static void main(String[] args) throws IOException {
		int exitCode = mainWithoutExitCall(args);

		System.exit(exitCode);
	}

	static int mainWithoutExitCall(String[] args) throws IOException {
		CommandLineArgsContainer container = new CommandLineArgsContainer();
		new JCommander(container).parse(args);

		PackageNameIncludeSet packageNamesToInclude = new PackageNameIncludeSet(container.packageNamesToInclude);
		PackageNameExcludeSet packageNamesToExclude = new PackageNameExcludeSet(container.packageNamesToExclude);

		InvalidChangeDetector detector = new InvalidChangeDetector(packageNamesToInclude, packageNamesToExclude);

		addMatchers(detector);

		List<InvalidChange<? extends JavaEntity>> invalidChanges;
		invalidChanges = detector.detectInvalidChanges(container.oldVersionJar, container.currentVersionJar);

		invalidChanges.forEach(System.out::println);

		int exitCode = invalidChanges.size();
		return exitCode;
	}

	public static void addMatchers(InvalidChangeDetector detector) {
		TypeMatch typeMatch;
		typeMatch = new TypeMatch();
		typeMatch.setDeprecated(false);
		typeMatch.setVisibility(Opcodes.ACC_PUBLIC);
		typeMatch.setCompareRemoved(true);
		typeMatch.setCompareReducedVisibility(false);
		detector.getTypeMatches().add(typeMatch);

		typeMatch = new TypeMatch();
		typeMatch.setDeprecated(false);
		typeMatch.setVisibility(Opcodes.ACC_PUBLIC);
		typeMatch.setCompareRemoved(false);
		typeMatch.setCompareReducedVisibility(true);
		detector.getTypeMatches().add(typeMatch);

		FieldMatch fieldMatch;
		fieldMatch = new FieldMatch();
		fieldMatch.setDeprecated(false);
		fieldMatch.setVisibility(Opcodes.ACC_PUBLIC);
		fieldMatch.setFieldDeprecated(false);
		fieldMatch.setFieldVisibility(Opcodes.ACC_PUBLIC);
		fieldMatch.setCompareFieldRemoved(true);
		fieldMatch.setCompareFieldReducedVisibility(false);
		detector.getFieldMatches().add(fieldMatch);

		fieldMatch = new FieldMatch();
		fieldMatch.setDeprecated(false);
		fieldMatch.setVisibility(Opcodes.ACC_PUBLIC);
		fieldMatch.setFieldDeprecated(false);
		fieldMatch.setFieldVisibility(Opcodes.ACC_PUBLIC);
		fieldMatch.setCompareFieldRemoved(false);
		fieldMatch.setCompareFieldReducedVisibility(true);
		detector.getFieldMatches().add(fieldMatch);

		fieldMatch = new FieldMatch();
		fieldMatch.setDeprecated(false);
		fieldMatch.setVisibility(Opcodes.ACC_PUBLIC);
		fieldMatch.setFieldDeprecated(false);
		fieldMatch.setFieldVisibility(Opcodes.ACC_PROTECTED);
		fieldMatch.setCompareFieldRemoved(true);
		fieldMatch.setCompareFieldReducedVisibility(false);
		detector.getFieldMatches().add(fieldMatch);

		fieldMatch = new FieldMatch();
		fieldMatch.setDeprecated(false);
		fieldMatch.setVisibility(Opcodes.ACC_PUBLIC);
		fieldMatch.setFieldDeprecated(false);
		fieldMatch.setFieldVisibility(Opcodes.ACC_PROTECTED);
		fieldMatch.setCompareFieldRemoved(false);
		fieldMatch.setCompareFieldReducedVisibility(true);
		detector.getFieldMatches().add(fieldMatch);

		MethodMatch methodMatch;
		methodMatch = new MethodMatch();
		methodMatch.setDeprecated(false);
		methodMatch.setVisibility(Opcodes.ACC_PUBLIC);
		methodMatch.setMethodDeprecated(false);
		methodMatch.setMethodVisibility(Opcodes.ACC_PUBLIC);
		methodMatch.setCompareMethodRemoved(true);
		methodMatch.setCompareMethodReducedVisibility(false);
		detector.getMethodMatches().add(methodMatch);

		methodMatch = new MethodMatch();
		methodMatch.setDeprecated(false);
		methodMatch.setVisibility(Opcodes.ACC_PUBLIC);
		methodMatch.setMethodDeprecated(false);
		methodMatch.setMethodVisibility(Opcodes.ACC_PUBLIC);
		methodMatch.setCompareMethodRemoved(false);
		methodMatch.setCompareMethodReducedVisibility(true);
		detector.getMethodMatches().add(methodMatch);

		methodMatch = new MethodMatch();
		methodMatch.setDeprecated(false);
		methodMatch.setVisibility(Opcodes.ACC_PUBLIC);
		methodMatch.setMethodDeprecated(false);
		methodMatch.setMethodVisibility(Opcodes.ACC_PROTECTED);
		methodMatch.setCompareMethodRemoved(true);
		methodMatch.setCompareMethodReducedVisibility(false);
		detector.getMethodMatches().add(methodMatch);

		methodMatch = new MethodMatch();
		methodMatch.setDeprecated(false);
		methodMatch.setVisibility(Opcodes.ACC_PUBLIC);
		methodMatch.setMethodDeprecated(false);
		methodMatch.setMethodVisibility(Opcodes.ACC_PROTECTED);
		methodMatch.setCompareMethodRemoved(false);
		methodMatch.setCompareMethodReducedVisibility(true);
		detector.getMethodMatches().add(methodMatch);
	}

}
