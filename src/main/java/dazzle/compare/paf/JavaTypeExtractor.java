package dazzle.compare.paf;

import java.io.IOException;
import java.net.URL;
import java.util.jar.*;

import org.objectweb.asm.*;

public class JavaTypeExtractor {

	private final ClassVisitor classVisitor;

	public JavaTypeExtractor(ClassVisitor classVisitor) {
		super();
		this.classVisitor = classVisitor;
	}

	public void execute(URL jarFile) throws IOException {
		try (JarInputStream jarIn = new JarInputStream(jarFile.openStream())) {
			JarEntry entry;
			while ((entry = jarIn.getNextJarEntry()) != null) {
				if (entry.getName().endsWith(".class")) {
					handleClass(jarIn);
				}
			}
		}
	}

	private void handleClass(JarInputStream in) throws IOException {
		new ClassReader(in).accept(classVisitor, 0);
	}

}
