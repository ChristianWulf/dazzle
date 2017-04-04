package dazzle.compare;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import dazzle.compare.InvalidChange.InvalidChangeType;
import dazzle.read.*;

public class InvalidChangeDetector {

	public static final Set<String> ALLOW_ALL_PACKAGES = new HashSet<>();

	private final Set<String> packageNamesToInclude;

	public InvalidChangeDetector(Set<String> packageNamesToInclude) {
		this(packageNamesToInclude, Collections.emptySet());
	}

	public InvalidChangeDetector(Set<String> packageNamesToInclude, Set<String> packageNamesToExclude) {
		this.packageNamesToInclude = packageNamesToInclude;
		// TODO implement packageNamesToExclude
	}

	public List<InvalidChange<?>> detectInvalidChanges(URL oldVersionJar, URL currentVersionJar) throws IOException {
		PublicNonDeprecatedVisitor oldVersionVisitor = new PublicNonDeprecatedVisitor(); // extract public types only
		UrlWalker urlWalker = new UrlWalker(oldVersionVisitor);
		urlWalker.visitJar(oldVersionJar);

		AllVisitor currentVersionVisitor = new AllVisitor(); // extract all types
		urlWalker = new UrlWalker(currentVersionVisitor);
		urlWalker.visitJar(currentVersionJar);

		Map<String, JavaType> searchRepositoryType = buildSearchIndex(currentVersionVisitor.getTypes());
		List<InvalidChange<JavaType>> invalidTypeChanges = compareTypes(oldVersionVisitor.getTypes(),
				searchRepositoryType);

		Map<String, JavaField> searchRepositoryField = buildSearchIndex(currentVersionVisitor.getFields());
		List<InvalidChange<JavaField>> invalidFieldChanges = compareFields(oldVersionVisitor.getFields(),
				searchRepositoryField);

		Map<String, JavaMethod> searchRepositoryMethod = buildSearchIndex(currentVersionVisitor.getMethods());
		List<InvalidChange<JavaMethod>> invalidMethodChanges = compareMethods(oldVersionVisitor.getMethods(),
				searchRepositoryMethod);

		List<InvalidChange<?>> invalidChanges = new ArrayList<>();
		invalidChanges.addAll(invalidTypeChanges);
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

	private List<InvalidChange<JavaType>> compareTypes(List<JavaType> oldVersionTypes,
			Map<String, JavaType> searchRepository) {
		List<InvalidChange<JavaType>> invalidChanges = new ArrayList<>();

		for (JavaType oldType : oldVersionTypes) {
			compareType(invalidChanges, oldType, searchRepository);
		}

		return invalidChanges;
	}

	private void compareType(List<InvalidChange<JavaType>> invalidChanges, JavaType oldType,
			Map<String, JavaType> searchRepository) {
		// skip types outside of the given packages
		if (!packageNamesToInclude.isEmpty() && !packageNamesToInclude.contains(oldType.getPackageName())) {
			return;
		}

		if (!searchRepository.containsKey(oldType.getFqn())) {
			invalidChanges.add(new InvalidChange<>(oldType, null, InvalidChangeType.TYPE_REMOVED));
			return;
		}

		JavaType currentType = searchRepository.get(oldType.getFqn());
		if (!currentType.isPublic()) {
			invalidChanges.add(new InvalidChange<>(oldType, currentType, InvalidChangeType.TYPE_VISIBILITY_CHANGED));
			return;
		}
	}

	private List<InvalidChange<JavaField>> compareFields(List<JavaField> fields,
			Map<String, JavaField> searchRepositoryField) {
		List<InvalidChange<JavaField>> invalidChanges = new ArrayList<>();

		for (JavaField javaField : fields) {
			compareField(invalidChanges, javaField, searchRepositoryField);
		}

		return invalidChanges;
	}

	private void compareField(List<InvalidChange<JavaField>> invalidChanges, JavaField oldField,
			Map<String, JavaField> searchRepository) {

		if (!searchRepository.containsKey(oldField.getKey())) {
			invalidChanges.add(new InvalidChange<>(oldField, null, InvalidChangeType.FIELD_REMOVED));
			return;
		}

		JavaField currentField = searchRepository.get(oldField.getKey());
		if (oldField.isPublic() && !currentField.isPublic()) {
			invalidChanges.add(new InvalidChange<>(oldField, currentField, InvalidChangeType.FIELD_VISIBILITY_CHANGED));
			return;
		}

		if (!oldField.getTypeName().equals(currentField.getTypeName())) {
			invalidChanges.add(new InvalidChange<>(oldField, currentField, InvalidChangeType.FIELD_TYPE_CHANGED));
			return;
		}
	}

	private List<InvalidChange<JavaMethod>> compareMethods(List<JavaMethod> methods,
			Map<String, JavaMethod> searchRepositoryMethod) {
		List<InvalidChange<JavaMethod>> invalidChanges = new ArrayList<>();
		// TODO Auto-generated method stub
		return invalidChanges;
	}

}
