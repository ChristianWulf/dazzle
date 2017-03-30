package dazzle.read;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ClassPathCrawler {

	public List<URL> getRootUrls () {
		List<URL> result = new ArrayList<> ();

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		while (cl != null) {
			if (cl instanceof URLClassLoader) {
				URL[] urls = ((URLClassLoader) cl).getURLs();
				result.addAll (Arrays.asList (urls));
			}
			cl = cl.getParent();
		}
		return result;
	}
}
