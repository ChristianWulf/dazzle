package dazzle.compare;

import java.util.*;

import dazzle.compare.InvalidChange.InvalidChangeType;
import dazzle.read.JavaField;

public class InvalidFieldDetector {

	private final List<InvalidChange<JavaField>> invalidChanges = new ArrayList<>();
	private final Map<String, JavaField> searchRepository;
	private final IncludeSet<String> packageNamesToInclude;
	private final ExcludeSet<String> packageNamesToExclude;

	public InvalidFieldDetector(Map<String, JavaField> searchRepository, IncludeSet<String> packageNamesToInclude,
			ExcludeSet<String> packageNamesToExclude) {
		super();
		this.searchRepository = searchRepository;
		this.packageNamesToInclude = packageNamesToInclude;
		this.packageNamesToExclude = packageNamesToExclude;
	}

	public List<InvalidChange<JavaField>> getInvalidChanges() {
		return invalidChanges;
	}

	public void detect(JavaField oldField) {
		JavaField currentField = searchRepository.get(oldField.getKey());

		if (!packageNamesToInclude.contains(oldField.getOwningType().getPackageName())) {
			return;
		}

		if (packageNamesToExclude.contains(currentField.getOwningType().getPackageName())) {
			return;
		}

		compare(oldField, currentField);
	}

	private void compare(JavaField oldField, JavaField currentField) {
		if (null == currentField) {
			invalidChanges.add(new InvalidChange<>(oldField, null, InvalidChangeType.FIELD_REMOVED));
			return;
		}

		if (oldField.isPublic() && !currentField.isPublic()) {
			invalidChanges.add(new InvalidChange<>(oldField, currentField, InvalidChangeType.FIELD_VISIBILITY_CHANGED));
			return;
		}

		if (!oldField.getTypeName().equals(currentField.getTypeName())) {
			invalidChanges.add(new InvalidChange<>(oldField, currentField, InvalidChangeType.FIELD_TYPE_CHANGED));
			return;
		}
	}
}
