package abc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URL;
import java.nio.file.Paths;

import org.junit.Test;

public class MainTest {

	@Test
	public void shouldParseArgsCorrectly() throws Exception {
		URL oldVersionResource = MainTest.class.getResource("/teetime-1.0.jar");
		String oldVersionJarPath = Paths.get(oldVersionResource.toURI()).toString();

		URL currentVersionResource = MainTest.class.getResource("/teetime-2.0.jar");
		String currentVersionJarPath = Paths.get(currentVersionResource.toURI()).toString();

		String[] args = { "-o", oldVersionJarPath, "-c", currentVersionJarPath };
		int numInvalidChanges = Main.mainWithoutExitCall(args);

		// 74 = TypeRemovedRule
		// 02 = TypeVisibilityReducedRule
		// 49 = MethodRemovedRule
		assertThat(numInvalidChanges, is(74 + 02 + 49));
	}
}
