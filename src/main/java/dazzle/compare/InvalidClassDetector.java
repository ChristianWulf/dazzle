package dazzle.compare;

import java.util.*;

import dazzle.compare.InvalidChange.InvalidChangeType;
import dazzle.read.JavaType;

public class InvalidClassDetector {

	private final List<InvalidChange<JavaType>> invalidChanges = new ArrayList<>();
	private final Map<String, JavaType> searchRepository;

	private final PackageNameIncludeSet packageNamesToInclude;
	private final PackageNameExcludeSet packageNamesToExclude;

	public InvalidClassDetector(Map<String, JavaType> searchRepository, PackageNameIncludeSet packageNamesToInclude,
			PackageNameExcludeSet packageNamesToExclude) {
		super();
		this.searchRepository = searchRepository;
		this.packageNamesToInclude = packageNamesToInclude;
		this.packageNamesToExclude = packageNamesToExclude;
	}

	public List<InvalidChange<JavaType>> getInvalidChanges() {
		return invalidChanges;
	}

	public void detect(JavaType oldType) {
		if (!packageNamesToInclude.contains(oldType.getPackageName())) {
			return;
		}

		if (packageNamesToExclude.contains(oldType.getPackageName())) {
			return;
		}

		JavaType currentType = searchRepository.get(oldType.getFqn());
		compare(oldType, currentType);
	}

	private void compare(JavaType oldType, JavaType currentType) {
		if (null == currentType) {
			invalidChanges.add(new InvalidChange<>(oldType.getFqn(), oldType, null, InvalidChangeType.TYPE_REMOVED));
			return;
		}

		if (!currentType.isPublic()) {
			invalidChanges.add(new InvalidChange<>(oldType.getFqn(), oldType, currentType, InvalidChangeType.TYPE_VISIBILITY_CHANGED));
			return;
		}
	}
}
