package abc;

import java.util.*;

public class PackageNameExcludeSet {

	private final Set<String> excludes;

	public PackageNameExcludeSet(List<String> excludes) {
		this(new HashSet<>(excludes));
	}

	public PackageNameExcludeSet(Set<String> includes) {
		super();
		this.excludes = includes;
	}

	public boolean contains(String element) {
		for (String exclude : excludes) {
			if (element.startsWith(exclude)) {
				return true;
			}
		}
		return false;
	}

}
