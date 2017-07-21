# dazzle
Detects invalid changes from one version to another version of an application.

For example, an invalid change is a change of a public type, field, or method which has not been marked with the @Deprecated annotation in the version prior to the change.

# Generate jar file
After executing "mvn package", you find the jar-file in the "target" folder.

# General Execution Pattern
`java -cp 'jar-file' dazzle.console.Main 'oldVersionJarFilePath' 'currentVersionJarFilePath' 'comma separated packageNames'`

### Example Executions
Search for invalid changes within the package `teetime.framework` by using `--include teetime/framework`:

`java -cp 'jar-file' dazzle.console.Main --old src/test/resources/teetime-1.0-jar --current src/test/resources/teetime-2.0-jar --include teetime/framework`

If you skip the "include" parameter, all packages are included:

`java -cp 'jar-file' dazzle.console.Main --old src/test/resources/teetime-1.0-jar --current src/test/resources/teetime-2.0-jar`
