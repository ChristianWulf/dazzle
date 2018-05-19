package dazzle.newcompare;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dazzle.matcher.FieldMatch;
import dazzle.matcher.MethodMatch;
import dazzle.matcher.PreviousJarVisitor;
import dazzle.matcher.TypeMatch;
import dazzle.read.JavaEntity;
import dazzle.read.UrlWalker;

public class InvalidChangeDetector {

	public static final PackageNameIncludeSet ALLOW_ALL_PACKAGES = new PackageNameIncludeSet(Collections.emptySet());

	private final PackageNameIncludeSet packageNamesToInclude;
	private final PackageNameExcludeSet packageNamesToExclude;
	private final List<TypeMatch> typeMatches = new ArrayList<>();
	private final List<FieldMatch> fieldMatches = new ArrayList<>();
	private final List<MethodMatch> methodMatches = new ArrayList<>();

	public InvalidChangeDetector(PackageNameIncludeSet packageNamesToInclude) {
		this(packageNamesToInclude, new PackageNameExcludeSet(Collections.emptySet()));
	}

	public InvalidChangeDetector(PackageNameIncludeSet packageNamesToInclude,
			PackageNameExcludeSet packageNamesToExclude) {
		this.packageNamesToInclude = packageNamesToInclude;
		this.packageNamesToExclude = packageNamesToExclude;
	}

	public List<InvalidChange<? extends JavaEntity>> detectInvalidChanges(URL oldVersionJar, URL currentVersionJar)
			throws IOException {
		UrlWalker urlWalker;

		ExtractEntityVisitor currentVersionVisitor = new ExtractEntityVisitor();
		urlWalker = new UrlWalker(currentVersionVisitor);
		urlWalker.visitJar(currentVersionJar);

		PreviousJarVisitor oldVersionVisitor = new PreviousJarVisitor(currentVersionVisitor);
		oldVersionVisitor.setPackageNamesToInclude(packageNamesToInclude);
		oldVersionVisitor.setPackageNamesToExclude(packageNamesToExclude);
		oldVersionVisitor.getTypeMatches().addAll(typeMatches);
		oldVersionVisitor.getFieldMatches().addAll(fieldMatches);
		oldVersionVisitor.getMethodMatches().addAll(methodMatches);

		urlWalker = new UrlWalker(oldVersionVisitor);
		urlWalker.visitJar(oldVersionJar);

		return oldVersionVisitor.getInvalidChanges();
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
