package dazzle.compare;

import java.util.*;

public class IncludeSet<T> {

	private final Set<T> includes;

	public IncludeSet(List<T> includes) {
		this(new HashSet<>(includes));
	}

	public IncludeSet(Set<T> includes) {
		super();
		this.includes = includes;
	}

	public boolean contains(T element) {
		if (includes.isEmpty()) return true;
		return includes.contains(element);
	}

}
