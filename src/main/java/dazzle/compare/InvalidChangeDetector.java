package dazzle.compare;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import dazzle.read.*;

public class InvalidChangeDetector {

	public static final IncludeSet<String> ALLOW_ALL_PACKAGES = new IncludeSet<>(Collections.emptySet());

	private final IncludeSet<String> packageNamesToInclude;
	private final ExcludeSet<String> packageNamesToExclude;

	public InvalidChangeDetector(IncludeSet<String> packageNamesToInclude) {
		this(packageNamesToInclude, new ExcludeSet<>(Collections.emptySet()));
	}

	public InvalidChangeDetector(IncludeSet<String> packageNamesToInclude, ExcludeSet<String> packageNamesToExclude) {
		this.packageNamesToInclude = packageNamesToInclude;
		this.packageNamesToExclude = packageNamesToExclude;
	}

	public List<InvalidChange<?>> detectInvalidChanges(URL oldVersionJar, URL currentVersionJar) throws IOException {
		PublicNonDeprecatedVisitor oldVersionVisitor = new PublicNonDeprecatedVisitor(); // extract public types only
		UrlWalker urlWalker = new UrlWalker(oldVersionVisitor);
		urlWalker.visitJar(oldVersionJar);

		AllVisitor currentVersionVisitor = new AllVisitor(); // extract all types
		urlWalker = new UrlWalker(currentVersionVisitor);
		urlWalker.visitJar(currentVersionJar);

		Map<String, JavaType> searchRepositoryType = buildSearchIndex(currentVersionVisitor.getTypes());
		InvalidClassDetector invalidClassDetector = new InvalidClassDetector(searchRepositoryType,
				packageNamesToInclude, packageNamesToExclude);
		oldVersionVisitor.getTypes().forEach(oldType -> invalidClassDetector.detect(oldType));

		Map<String, JavaField> searchRepositoryField = buildSearchIndex(currentVersionVisitor.getFields());
		InvalidFieldDetector invalidFieldDetector = new InvalidFieldDetector(searchRepositoryField,
				packageNamesToInclude, packageNamesToExclude);
		oldVersionVisitor.getFields().forEach(oldField -> invalidFieldDetector.detect(oldField));

		Map<String, JavaMethod> searchRepositoryMethod = buildSearchIndex(currentVersionVisitor.getMethods());
		InvalidMethodDetector invalidMethodDetector = new InvalidMethodDetector(searchRepositoryMethod,
				packageNamesToInclude, packageNamesToExclude);
		oldVersionVisitor.getMethods().forEach(oldMethod -> invalidMethodDetector.detect(oldMethod));

		List<InvalidChange<?>> invalidChanges = new ArrayList<>();
		invalidChanges.addAll(invalidClassDetector.getInvalidChanges());
		invalidChanges.addAll(invalidFieldDetector.getInvalidChanges());
		// invalidChanges.addAll(invalidMethodDetector.getInvalidChanges());

		return invalidChanges;
	}

	private <T extends JavaEntity> Map<String, T> buildSearchIndex(List<T> filteredTypes) {
		Map<String, T> searchRepository = new HashMap<>();
		for (T filteredType : filteredTypes) {
			String key = filteredType.getKey();
			T value = filteredType;
			searchRepository.put(key, value);
		}
		return searchRepository;
	}

}
