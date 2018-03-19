package dazzle.newconsole;

import java.net.URL;
import java.nio.file.Paths;

import org.junit.Test;

import dazzle.compare.InvalidChangeDetectorTest;

public class MainTest {

	@Test
	public void shouldParseArgsCorrectly() throws Exception {
		URL oldVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-1.0.jar");
		String oldVersionJarPath = Paths.get(oldVersionResource.toURI()).toString();

		URL currentVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		String currentVersionJarPath = Paths.get(currentVersionResource.toURI()).toString();

		String[] args = { "-o", oldVersionJarPath, "-c", currentVersionJarPath };
		MainNew.mainWithoutExitCall(args);
	}

	@Test
	public void shouldParseArgsWithIncludeCorrectly() throws Exception {
		URL oldVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-1.0.jar");
		String oldVersionJarPath = Paths.get(oldVersionResource.toURI()).toString();

		URL currentVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		String currentVersionJarPath = Paths.get(currentVersionResource.toURI()).toString();

		String[] args = { "-o", oldVersionJarPath, "-c", currentVersionJarPath, "-i", "teetime/framework" };
		MainNew.mainWithoutExitCall(args);
	}

	@Test
	public void shouldParseArgsWithMultipleIncludesFalsely() throws Exception {
		URL oldVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-1.0.jar");
		String oldVersionJarPath = Paths.get(oldVersionResource.toURI()).toString();

		URL currentVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		String currentVersionJarPath = Paths.get(currentVersionResource.toURI()).toString();

		String[] args = { "-o", oldVersionJarPath, "-c", currentVersionJarPath, "-i",
		"teetime/framework, teetime/stage" };
		MainNew.mainWithoutExitCall(args);
	}

	@Test
	public void shouldParseArgsWithMultipleIncludesCorrectly() throws Exception {
		URL oldVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-1.0.jar");
		String oldVersionJarPath = Paths.get(oldVersionResource.toURI()).toString();

		URL currentVersionResource = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		String currentVersionJarPath = Paths.get(currentVersionResource.toURI()).toString();

		String[] args = { "-o", oldVersionJarPath, "-c", currentVersionJarPath, "-i", "teetime/framework", "-i",
		"teetime/stage" };
		MainNew.mainWithoutExitCall(args);
	}

}
