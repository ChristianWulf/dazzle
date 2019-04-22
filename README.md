# dazzle
Detects invalid changes from one version to another version of an application. A similar, but outdated tool is [clirr](http://clirr.sourceforge.net/index.html).

For example, an invalid change is a change of a public type, field, or method which has not been marked with the @Deprecated annotation in the version prior to the change.

# Build/Generate the Jar File
After executing "mvn package", you find the jar-file with and without dependencies in the "target" folder.

# General Execution Pattern
`java -cp 'jar-file' abc.Main --old 'oldVersionJarFilePath' --current 'currentVersionJarFilePath' [--include 'comma separated packageNames'] [--rulesFile 'rulesFile']`

The exit code represents the number of invalid changes found in the given application. So, if no invalid changes were found, the exit code is 0. Otherwise, the exit code is greater than 0. In this way, dazzle can be easily integrated into your build process and fail the build process upon invalid changes.

### Example Executions
Search for invalid changes within the package `teetime.framework` by using `--include teetime/framework`:

`java -cp 'jar-file' abc.Main --old src/test/resources/teetime-1.0-jar --current src/test/resources/teetime-2.0-jar --include teetime/framework`

If you skip the "include" parameter, all packages are included:

`java -cp 'jar-file' abc.Main --old src/test/resources/teetime-1.0-jar --current src/test/resources/teetime-2.0-jar`

By default, all of the rules are applied which are listed in the rules file located at `classpath:/rules.properties`. You can change the location of the rules file by adding the argument `--rulesFile`:

`java -cp 'jar-file' abc.Main --old src/test/resources/teetime-1.0-jar --current src/test/resources/teetime-2.0-jar --rulesFile ./rules-list.properties`


# Adding Your Own Rule
To add your own rule, create a class and let it extend `abc.crawler.AbcRule`. Afterwards, implement the required methods.

### Example Rules
Have a look at the rules which are provided by this project in the package `abc.rules`.
