package abc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;

import com.beust.jcommander.JCommander;

import abc.crawler.AbcClassLoader;
import abc.crawler.AbcRule;
import abc.crawler.CurrentJarInventory;
import abc.crawler.CurrentJarInventoryBuilder;
import abc.crawler.JarCrawler;
import abc.matcher.InvalidChange;
import dazzle.read.JavaEntity;

public class Main {

	public static void main(String[] args) throws IOException {
		int exitCode = mainWithoutExitCall(args);

		System.exit(exitCode);
	}

	public static int mainWithoutExitCall(String[] args) throws IOException {
		CommandLineArgsContainer container = extractCommandLineArguments(args);

		List<InvalidChange<? extends JavaEntity>> invalidChanges = detectInvalidChanges(container);

		int exitCode = invalidChanges.size();
		return exitCode;
	}

	private static CommandLineArgsContainer extractCommandLineArguments(String[] args) {
		CommandLineArgsContainer container = new CommandLineArgsContainer();
		new JCommander(container).parse(args);
		return container;
	}

	private static List<InvalidChange<? extends JavaEntity>> detectInvalidChanges(CommandLineArgsContainer container)
			throws IOException {
		List<InvalidChange<? extends JavaEntity>> invalidChanges = new ArrayList<>();

		CurrentJarInventoryBuilder currentJarInventoryVisitor = new CurrentJarInventoryBuilder();

		// crawl the new jar to build up the current jar inventory
		JarCrawler newJarCrawler = new JarCrawler();
		newJarCrawler.visitJar(container.currentVersionJar, currentJarInventoryVisitor);

		// import all available rule
		List<ClassVisitor> rules;
		try {
			rules = readRulesFromConfig(currentJarInventoryVisitor.getCurrentJarInventory());
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}

		// add a subset of all available rules to the visitor
		HierarchyVisitor visitor = new HierarchyVisitor();
		rules.forEach(rule -> visitor.addClassVisitor(rule));

		// crawl the old jar to detect removed types, fields and methods
		JarCrawler oldJarCrawler = new JarCrawler();
		oldJarCrawler.visitJar(container.oldVersionJar, visitor);

		// TODO Auto-generated method stub
		return invalidChanges;
	}

	private static List<ClassVisitor> readRulesFromConfig(CurrentJarInventory currentJarInventory)
			throws URISyntaxException, IOException {
		URL resource = Main.class.getResource("/rules.properties");
		Path path = Paths.get(resource.toURI());
		List<String> lines = Files.readAllLines(path);

		List<ClassVisitor> rules = new ArrayList<>();

		AbcClassLoader<AbcRule> classLoader = new AbcClassLoader<>();
		lines.forEach(line -> {
			AbcRule rule = classLoader.load(line);
			rule.setCurrentJarInventory(currentJarInventory);
			rules.add(rule);
		});
		return rules;
	}

}
