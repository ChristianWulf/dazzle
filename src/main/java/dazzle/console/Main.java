package dazzle.console;

import java.io.IOException;
import java.net.*;
import java.nio.file.Paths;
import java.util.*;

import dazzle.compare.*;

public class Main {

	public static void main(String[] args) throws IOException {
		URL oldVersionJar = toURL(args[0]);
		URL currentVersionJar = toURL(args[1]);
		// TODO consider includes and excludes
		Set<String> packageNames = toSet(args[2]);

		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(packageNames);
		List<InvalidChange> invalidChanges = invalidChangeDetector.detectInvalidChanges(oldVersionJar, currentVersionJar);

		for (InvalidChange invalidChange : invalidChanges) {
			// TODO consider to use slf4j
			System.out.println(invalidChange);
		}

		System.exit(invalidChanges.size());
	}

	private static Set<String> toSet(String commaSeparatedPackages) {
		String[] split = commaSeparatedPackages.split(",");
		return new HashSet<>(Arrays.asList(split));
	}

	private static URL toURL(String filePath) throws MalformedURLException {
		return Paths.get(filePath).toUri().toURL();
	}
}
