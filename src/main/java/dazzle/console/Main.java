package dazzle.console;

import java.io.IOException;
import java.util.List;

import com.beust.jcommander.JCommander;

import dazzle.compare.*;

public class Main {

	public static void main(String[] args) throws IOException {
		int exitCode = mainWithoutExitCall(args);

		System.exit(exitCode);
	}

	static int mainWithoutExitCall(String[] args) throws IOException {
		CommandLineArgsContainer container = new CommandLineArgsContainer();
		new JCommander(container).parse(args);

		IncludeSet<String> packageNamesToInclude = new IncludeSet<>(container.packageNamesToInclude);
		ExcludeSet<String> packageNamesToExclude = new ExcludeSet<>(container.packageNamesToExclude);

		InvalidChangeDetector invalidChangeDetector = new InvalidChangeDetector(packageNamesToInclude,
				packageNamesToExclude);
		List<InvalidChange<?>> invalidChanges = invalidChangeDetector.detectInvalidChanges(container.oldVersionJar,
				container.currentVersionJar);

		for (InvalidChange<?> invalidChange : invalidChanges) {
			// TODO consider to use slf4j
			System.out.println(invalidChange);
		}

		int exitCode = invalidChanges.size();
		return exitCode;
	}
}
