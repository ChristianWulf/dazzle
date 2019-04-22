package abc.crawler;

import java.util.Map;

import abc.java.JavaField;
import abc.java.JavaMethod;
import abc.java.JavaType;

public interface CurrentJarInventory {

	public Map<String, JavaType> getTypes();

	public Map<String, JavaField> getFields();

	public Map<String, JavaMethod> getMethods();
}
