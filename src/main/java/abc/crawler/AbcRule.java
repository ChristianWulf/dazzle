package abc.crawler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class AbcRule extends ClassVisitor {

	private CurrentJarInventory currentJarInventory;

	protected AbcRule() {
		super(Opcodes.ASM5);
	}

	public void setCurrentJarInventory(CurrentJarInventory currentJarInventory) {
		this.currentJarInventory = currentJarInventory;
	}

	protected CurrentJarInventory getCurrentJarInventory() {
		return currentJarInventory;
	}
}
