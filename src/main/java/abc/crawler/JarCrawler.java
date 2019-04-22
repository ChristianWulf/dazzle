package abc.crawler;

import java.io.IOException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.objectweb.asm.ClassVisitor;

public class JarCrawler {

	public void visitJar(URL url, ClassVisitor visitor) throws IOException {
		ClassFileParser classFileParser = new ClassFileParser();

		try (JarInputStream jarIn = new JarInputStream(url.openStream())) {
			JarEntry entry;
			while ((entry = jarIn.getNextJarEntry()) != null) {
				if (entry.getName().endsWith(".class")) {
					classFileParser.parse(jarIn, visitor);
				}
			}
		}
	}
}
