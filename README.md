# dazzle
Detects invalid changes from one version to another version of an application.

# Generate jar file
After executing "mvn package", you find the jar-file in the "target" folder.

# Execution
java -cp 'jar-file' dazzle.console.Main 'oldVersionJarFilePath' 'currentVersionJarFilePath' 'comma separated packageNames'

## Examples
"teetime/framework" means "search for invalid changes within the package teetime.framework"

`java -cp 'jar-file' dazzle.console.Main src/test/resources/teetime-1.0-jar src/test/resources/teetime-2.0-jar teetime/framework`

"-a" means "search for invalid changes within all packages"

`java -cp 'jar-file' dazzle.console.Main src/test/resources/teetime-1.0-jar src/test/resources/teetime-2.0-jar -a`
