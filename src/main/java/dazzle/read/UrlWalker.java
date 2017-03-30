package dazzle.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class UrlWalker {

	private final Visitor visitor;

	public UrlWalker(Visitor visitor) {
		super();
		this.visitor = visitor;
	}

	public void visit(List<URL> rootUrls) throws IOException {
		for (URL url : rootUrls) {
			File f = new File(url.getPath());
			if (f.isDirectory()) {
				visitFile(f);
			} else {
				visitJar(url);
			}
		}
	}

	public void visitFile(File f) throws IOException {
		if (f.isDirectory()) {
			final File[] children = f.listFiles();
			if (children != null) {
				for (File child : children) {
					visitFile(child);
				}
			}
		} else if (f.getName().endsWith(".class")) {
			try (FileInputStream in = new FileInputStream(f)) {
				visitor.handleClass(in);
			}
		}
	}

	public void visitJar(URL url) throws IOException {
		try (JarInputStream jarIn = new JarInputStream(url.openStream())) {
			JarEntry entry;
			while ((entry = jarIn.getNextJarEntry()) != null) {
				if (entry.getName().endsWith(".class")) {
					visitor.handleClass(jarIn);
				}
			}
		}
	}

}
