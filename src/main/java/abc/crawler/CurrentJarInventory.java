package abc.crawler;

import java.util.Map;

import dazzle.read.JavaField;
import dazzle.read.JavaMethod;
import dazzle.read.JavaType;

public interface CurrentJarInventory {

	public Map<String, JavaType> getTypes();

	public Map<String, JavaField> getFields();

	public Map<String, JavaMethod> getMethods();
}
