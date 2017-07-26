package dazzle.compare;

import java.util.*;

import dazzle.compare.InvalidChange.InvalidChangeType;
import dazzle.read.JavaMethod;

public class InvalidMethodDetector {

	private final List<InvalidChange<JavaMethod>> invalidChanges = new ArrayList<>();
	private final Map<String, JavaMethod> searchRepository;
	private final PackageNameIncludeSet packageNamesToInclude;
	private final PackageNameExcludeSet packageNamesToExclude;

	public InvalidMethodDetector(Map<String, JavaMethod> searchRepository, PackageNameIncludeSet packageNamesToInclude,
			PackageNameExcludeSet packageNamesToExclude) {
		super();
		this.searchRepository = searchRepository;
		this.packageNamesToInclude = packageNamesToInclude;
		this.packageNamesToExclude = packageNamesToExclude;
	}

	public List<InvalidChange<JavaMethod>> getInvalidChanges() {
		return invalidChanges;
	}

	public void detect(JavaMethod oldMethod) {
		String packageName = oldMethod.getOwningType().getPackageName();
		if (!packageNamesToInclude.contains(packageName)) {
			return;
		}

		if (packageNamesToExclude.contains(packageName)) {
			return;
		}

		JavaMethod currentMethod = searchRepository.get(oldMethod.getKey());
		compare(oldMethod, currentMethod);
	}

	private void compare(JavaMethod oldMethod, JavaMethod currentMethod) {
		boolean isPublicOrProtected = oldMethod.isPublic() || oldMethod.isProtected();
		boolean isTypePublicOrProtected = oldMethod.getOwningType().isPublic()
				|| oldMethod.getOwningType().isProtected();

		if (isTypePublicOrProtected && isPublicOrProtected && null == currentMethod) {
			invalidChanges.add(new InvalidChange<>(oldMethod.getOwningType().getFqn(), oldMethod, null,
					InvalidChangeType.METHOD_REMOVED));
			return;
		}

		if (oldMethod.isPublic() && !currentMethod.isPublic()) {
			invalidChanges.add(new InvalidChange<>(oldMethod.getOwningType().getFqn(), oldMethod, currentMethod,
					InvalidChangeType.METHOD_VISIBILITY_CHANGED));
			return;
		}

		if (isTypePublicOrProtected && isPublicOrProtected
				&& !oldMethod.getReturnTypeName().equals(currentMethod.getReturnTypeName())) {
			invalidChanges.add(new InvalidChange<>(oldMethod.getOwningType().getFqn(), oldMethod, currentMethod,
					InvalidChangeType.METHOD_RETURNTYPE_CHANGED));
			return;
		}

		// if (!oldMethod.getParameterSignature().equals(currentMethod.getParameterSignature())) {
		// invalidChanges.add(new InvalidChange<>(oldMethod, currentMethod,
		// InvalidChangeType.METHOD_PARAMETERTYPES_REMOVED));
		// return;
		// }
	}
}
