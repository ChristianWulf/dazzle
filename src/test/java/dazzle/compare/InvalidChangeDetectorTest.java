package dazzle.compare;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import dazzle.newcompare.InvalidChange;
import dazzle.newcompare.InvalidChangeDetector;
import dazzle.newcompare.PackageNameExcludeSet;
import dazzle.newcompare.PackageNameIncludeSet;
import dazzle.newconsole.MainNew;
import dazzle.read.JavaEntity;

public class InvalidChangeDetectorTest {

	// These tests are valid for types only. Fields and methods must be commented out.
	
	@Test
	public void testShouldDetectInvalidChangesBetweenVersion1And2WithinAllPackages() throws Exception {
		URL oldVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-1.0.jar");
		URL currentVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");

		PackageNameIncludeSet packageNames = InvalidChangeDetector.ALLOW_ALL_PACKAGES;
		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(packageNames);
		
		MainNew.addMatchers(invalidChangeDetector);
		
		List<InvalidChange<? extends JavaEntity>> detectInvalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar,
				currentVersionJar);

		assertThat(detectInvalidChanges, hasSize(76));
	}

	@Test
	public void testShouldDetectInvalidChangesBetweenVersion20And21WithinAllApackages() throws Exception {
		URL oldVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		URL currentVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.1.jar");

		PackageNameIncludeSet packageNames = InvalidChangeDetector.ALLOW_ALL_PACKAGES;
		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(packageNames);
		
		MainNew.addMatchers(invalidChangeDetector);
		
		List<InvalidChange<? extends JavaEntity>> detectInvalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar,
				currentVersionJar);

		assertThat(detectInvalidChanges, hasSize(12));
	}

	@Test
	public void testShouldDetectInvalidChangesWithInclude() throws Exception {
		URL oldVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		URL currentVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.1.jar");

		PackageNameIncludeSet includedPackageNames = new PackageNameIncludeSet(Arrays.asList("teetime/framework"));
		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(includedPackageNames);
		
		MainNew.addMatchers(invalidChangeDetector);
		
		List<InvalidChange<? extends JavaEntity>> detectInvalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar,
				currentVersionJar);

		assertThat(detectInvalidChanges, hasSize(8));
	}

	@Test
	public void testShouldDetectInvalidChangesWithIncludeAndExclude() throws Exception {
		URL oldVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.0.jar");
		URL currentVersionJar = InvalidChangeDetectorTest.class.getResource("/teetime-2.1.jar");

		PackageNameIncludeSet includedPackageNames = new PackageNameIncludeSet(Arrays.asList("teetime/framework"));
		PackageNameExcludeSet excludedPackageNames = new PackageNameExcludeSet(Arrays.asList("teetime/framework/pipe"));
		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(includedPackageNames, excludedPackageNames);
		
		MainNew.addMatchers(invalidChangeDetector);
		
		List<InvalidChange<? extends JavaEntity>> detectInvalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar,
				currentVersionJar);

		assertThat(detectInvalidChanges, hasSize(4));
	}
}
