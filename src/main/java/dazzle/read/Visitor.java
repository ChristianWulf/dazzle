package dazzle.read;

import java.io.IOException;
import java.io.InputStream;

public interface Visitor {

	void handleClass(InputStream in) throws IOException;

}
