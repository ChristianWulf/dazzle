package dazzle.compare;

import java.util.*;

import dazzle.compare.InvalidChange.InvalidChangeType;
import dazzle.read.JavaMethod;

public class InvalidMethodDetector {

	private final List<InvalidChange<JavaMethod>> invalidChanges = new ArrayList<>();
	private final Map<String, JavaMethod> searchRepository;

	public InvalidMethodDetector(Map<String, JavaMethod> searchRepository) {
		super();
		this.searchRepository = searchRepository;
	}

	public List<InvalidChange<JavaMethod>> getInvalidChanges() {
		return invalidChanges;
	}

	public void detect(JavaMethod oldMethod) {
		JavaMethod currentMethod = searchRepository.get(oldMethod.getKey());
		compare(oldMethod, currentMethod);
	}

	private void compare(JavaMethod oldMethod, JavaMethod currentMethod) {
		if (oldMethod.isPublic() && null == currentMethod) {
			invalidChanges.add(new InvalidChange<>(oldMethod, null, InvalidChangeType.METHOD_REMOVED));
			return;
		}

		if (oldMethod.isPublic() && !currentMethod.isPublic()) {
			invalidChanges
			.add(new InvalidChange<>(oldMethod, currentMethod, InvalidChangeType.METHOD_VISIBILITY_CHANGED));
			return;
		}

		if (oldMethod.isPublic() && !oldMethod.getReturnTypeName().equals(currentMethod.getReturnTypeName())) {
			invalidChanges
			.add(new InvalidChange<>(oldMethod, currentMethod, InvalidChangeType.METHOD_RETURNTYPE_CHANGED));
			return;
		}

		// if (!oldMethod.getParameterSignature().equals(currentMethod.getParameterSignature())) {
		// invalidChanges.add(new InvalidChange<>(oldMethod, currentMethod,
		// InvalidChangeType.METHOD_PARAMETERTYPES_REMOVED));
		// return;
		// }
	}
}
