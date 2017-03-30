package dazzle.compare;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.*;

import org.junit.Test;

public class InvalidChangeDetectorTest {

	@Test
	public void testShouldDetectInvalidChangesBetweenVersion1And2WithinAllPackages() throws Exception {
		URL oldVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-1.0.jar");
		URL currentVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");

		Set<String> packageNames = InvalidChangeDetector.ALLOW_ALL_PACKAGES;
		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(packageNames);
		List<InvalidChange> detectInvalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar,
				currentVersionJar);

		assertThat(detectInvalidChanges, hasSize(76));
	}

	@Test
	public void testShouldDetectInvalidChangesBetweenVersion2_0And2_1WithinAllApackages() throws Exception {
		URL oldVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		URL currentVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.1.jar");

		Set<String> packageNames = InvalidChangeDetector.ALLOW_ALL_PACKAGES;
		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(packageNames);
		List<InvalidChange> detectInvalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar,
				currentVersionJar);

		assertThat(detectInvalidChanges, hasSize(12));
	}

	@Test
	public void testShouldDetectInvalidChangesWithSinglePackageScope() throws Exception {
		URL oldVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		URL currentVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.1.jar");

		Set<String> packageNames = new HashSet<>(Arrays.asList("teetime/framework"));
		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(packageNames);
		List<InvalidChange> detectInvalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar,
				currentVersionJar);

		assertThat(detectInvalidChanges, hasSize(4));
	}
}
