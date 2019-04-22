package abc.crawler;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

public class ClassFileParser {

	/**
	 * Can be invoked multiple times.
	 */
	public void parse(InputStream in, ClassVisitor visitor) throws IOException {
		ClassReader classReader = new ClassReader(in);
		classReader.accept(visitor, 0);
	}
}
