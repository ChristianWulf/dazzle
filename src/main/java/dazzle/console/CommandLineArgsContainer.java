package dazzle.console;

import java.net.*;
import java.nio.file.Paths;
import java.util.*;

import com.beust.jcommander.*;

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

	@Parameter(names = { "--old", "-o" }, required = true, description = "An old version of the application", converter=UrlConverter.class)
	URL oldVersionJar;
	@Parameter(names = { "--current", "-c" }, required = true, description = "The current version of the application", converter=UrlConverter.class)
	URL currentVersionJar;
	@Parameter(names = { "--include",
	"-i" }, description = "Includes one or more package names of the old version. When skipping this option, all packages of the old version are included.")
	Set<String> packageNamesToInclude = new HashSet<>();
	@Parameter(names = { "--exclude",
	"-x" }, description = "Excludes one or more package names of the old version. When skipping this option, no packages of the old version are excluded.")
	Set<String> packageNamesToExclude = new HashSet<>();

}
