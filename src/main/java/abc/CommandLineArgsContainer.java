package abc;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;

class CommandLineArgsContainer {

	private static class UrlConverter implements IStringConverter<URL> {
		@Override
		public URL convert(String value) {
			try {
				return Paths.get(value).toUri().toURL();
			} catch (MalformedURLException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	@Parameter(names = { "--old",
			"-o" }, required = true, description = "An old version of the application", converter = UrlConverter.class)
	URL oldVersionJar;
	@Parameter(names = { "--current",
			"-c" }, required = true, description = "The current version of the application", converter = UrlConverter.class)
	URL currentVersionJar;

	@Parameter(names = { "--rulesFile",
			"-r" }, required = false, description = "The URL of the 'rules file'. It contains the fully qualified class names of the rules to use. When skipping this option, the internal 'fules file' is used.", converter = UrlConverter.class)
	URL rulesFiles = CommandLineArgsContainer.class.getResource("/rules.properties");

	@Parameter(names = { "--include",
			"-i" }, description = "Includes one or more package names of the old version. When skipping this option, all packages of the old version are included.")
	Set<String> packageNamesToInclude = new HashSet<>();
	@Parameter(names = { "--exclude",
			"-x" }, description = "Excludes one or more package names of the old version. When skipping this option, no packages of the old version are excluded.")
	Set<String> packageNamesToExclude = new HashSet<>();

}
