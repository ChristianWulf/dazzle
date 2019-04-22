package abc.rules;

import abc.crawler.AbcRule;
import dazzle.read.JavaType;

public class RemovedNonDeprecatedClass extends AbcRule {

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		JavaType javaType = getCurrentJarInventory().getTypes().get(name);
		if (javaType == null) {	// if removed
			// TODO lookup @Deprecated annotation 
			
		}
		
		// TODO Auto-generated method stub
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
