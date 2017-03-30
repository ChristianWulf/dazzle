package dazzle.compare;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import dazzle.compare.InvalidChange.InvalidChangeType;
import dazzle.read.*;

public class InvalidChangeDetector {

	public static final String ALL_PACKAGES_WILDCARD = "-a";
	public static final Set<String> ALLOW_ALL_PACKAGES = new HashSet<>(Arrays.asList(InvalidChangeDetector.ALL_PACKAGES_WILDCARD));

	private final Set<String> packageNames;

	public InvalidChangeDetector(Set<String> packageNames) {
		this.packageNames = packageNames;
	}

	public List<InvalidChange> detectInvalidChanges(URL oldVersionJar, URL currentVersionJar) throws IOException {
		PublicNonDeprecatedVisitor oldVersionVisitor = new PublicNonDeprecatedVisitor(); // extract public types only
		UrlWalker urlWalker = new UrlWalker(oldVersionVisitor);
		urlWalker.visitJar(oldVersionJar);

		AllVisitor currentVersionVisitor = new AllVisitor(); // extract all types
		urlWalker = new UrlWalker(currentVersionVisitor);
		urlWalker.visitJar(currentVersionJar);

		Map<String, JavaType> searchRepositoryType = buildSearchIndex(currentVersionVisitor.getTypes());
		List<InvalidChange> invalidTypeChanges = compareTypes(oldVersionVisitor.getTypes(), searchRepositoryType);

		Map<String, JavaField> searchRepositoryField = buildSearchIndex(currentVersionVisitor.getFields());
		List<InvalidChange> invalidFieldChanges = compareFields(oldVersionVisitor.getFields(), searchRepositoryField);

		Map<String, JavaMethod> searchRepositoryMethod = buildSearchIndex(currentVersionVisitor.getMethods());
		List<InvalidChange> invalidMethodChanges = compareMethods(oldVersionVisitor.getMethods(),
				searchRepositoryMethod);

		List<InvalidChange> invalidChanges = invalidTypeChanges;
		invalidChanges.addAll(invalidFieldChanges);
		invalidChanges.addAll(invalidMethodChanges);

		return invalidChanges;
	}

	private <T extends JavaEntity> Map<String, T> buildSearchIndex(List<T> filteredTypes) {
		HashMap<String, T> searchRepository = new HashMap<>();
		for (T filteredType : filteredTypes) {
			String key = filteredType.getKey();
			T value = filteredType;
			searchRepository.put(key, value);
		}
		return searchRepository;
	}

	private List<InvalidChange> compareTypes(List<JavaType> oldVersionTypes, Map<String, JavaType> searchRepository) {
		List<InvalidChange> invalidChanges = new ArrayList<>();

		for (JavaType oldType : oldVersionTypes) {
			compareType(invalidChanges, oldType, searchRepository);
		}

		return invalidChanges;
	}

	private void compareType(List<InvalidChange> invalidChanges, JavaType oldType,
			Map<String, JavaType> searchRepository) {
		// skip types outside of the given packages
		if (!packageNames.contains(ALL_PACKAGES_WILDCARD) && !packageNames.contains(oldType.getPackageName())) {
			return;
		}

		if (!searchRepository.containsKey(oldType.getFqn())) {
			invalidChanges.add(new InvalidChange(oldType, null, InvalidChangeType.TYPE_REMOVED));
			return;
		}

		JavaType currentType = searchRepository.get(oldType.getFqn());
		if (!currentType.isPublic()) {
			invalidChanges.add(new InvalidChange(oldType, currentType, InvalidChangeType.TYPE_VISIBILITY_CHANGED));
			return;
		}
	}

	private List<InvalidChange> compareFields(List<JavaField> fields, Map<String, JavaField> searchRepositoryField) {
		List<InvalidChange> invalidChanges = new ArrayList<>();
		// TODO Auto-generated method stub
		return invalidChanges;
	}

	private List<InvalidChange> compareMethods(List<JavaMethod> methods,
			Map<String, JavaMethod> searchRepositoryMethod) {
		List<InvalidChange> invalidChanges = new ArrayList<>();
		// TODO Auto-generated method stub
		return invalidChanges;
	}

}
