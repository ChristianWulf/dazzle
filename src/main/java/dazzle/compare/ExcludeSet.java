package dazzle.compare;

import java.util.Set;

public class ExcludeSet<T> {

	private final Set<T> excludes;

	public ExcludeSet(Set<T> includes) {
		super();
		this.excludes = includes;
	}

	public boolean contains(T element) {
		return excludes.contains(element);
	}

}
