package dazzle.compare;

import java.util.*;

public class PackageNameIncludeSet {

	private final Set<String> includes;

	public PackageNameIncludeSet(List<String> includes) {
		this(new HashSet<>(includes));
	}

	public PackageNameIncludeSet(Set<String> includes) {
		super();
		this.includes = includes;
	}

	public boolean contains(String element) {
		if (includes.isEmpty()) return true;
		for (String include : includes) {
			if (element.startsWith(include)) {
				return true;
			}
		}
		return false;
	}

}
