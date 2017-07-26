package dazzle.compare;

import java.util.*;

import dazzle.compare.InvalidChange.InvalidChangeType;
import dazzle.read.JavaField;

public class InvalidFieldDetector {

	private final List<InvalidChange<JavaField>> invalidChanges = new ArrayList<>();
	private final Map<String, JavaField> searchRepository;
	private final PackageNameIncludeSet packageNamesToInclude;
	private final PackageNameExcludeSet packageNamesToExclude;

	public InvalidFieldDetector(Map<String, JavaField> searchRepository, PackageNameIncludeSet packageNamesToInclude,
			PackageNameExcludeSet packageNamesToExclude) {
		super();
		this.searchRepository = searchRepository;
		this.packageNamesToInclude = packageNamesToInclude;
		this.packageNamesToExclude = packageNamesToExclude;
	}

	public List<InvalidChange<JavaField>> getInvalidChanges() {
		return invalidChanges;
	}

	public void detect(JavaField oldField) {
		String packageName = oldField.getOwningType().getPackageName();
		if (!packageNamesToInclude.contains(packageName)) {
			return;
		}

		if (packageNamesToExclude.contains(packageName)) {
			return;
		}

		JavaField currentField = searchRepository.get(oldField.getKey());
		compare(oldField, currentField);
	}

	private void compare(JavaField oldField, JavaField currentField) {
		boolean isPublicOrProtected = oldField.isPublic() || oldField.isProtected();
		boolean isTypePublicOrProtected = oldField.getOwningType().isPublic() || oldField.getOwningType().isProtected();

		if (isPublicOrProtected && isTypePublicOrProtected && null == currentField) {
			invalidChanges.add(new InvalidChange<>(oldField.getOwningType().getFqn(), oldField, null,
					InvalidChangeType.FIELD_REMOVED));
			return;
		}

		if (isTypePublicOrProtected && oldField.isPublic() && !currentField.isPublic()) {
			invalidChanges.add(new InvalidChange<>(oldField.getOwningType().getFqn(), oldField, currentField,
					InvalidChangeType.FIELD_VISIBILITY_CHANGED));
			return;
		}

		if (isPublicOrProtected && isTypePublicOrProtected
				&& !oldField.getTypeName().equals(currentField.getTypeName())) {
			invalidChanges.add(new InvalidChange<>(oldField.getOwningType().getFqn(), oldField, currentField,
					InvalidChangeType.FIELD_TYPE_CHANGED));
			return;
		}
	}
}
