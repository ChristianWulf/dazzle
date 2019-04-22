package abc.crawler;

import java.io.IOException;
import java.net.URL;

import dazzle.read.UrlWalker;

public class OldJarCrawler {

	private CurrentJarInventory currentJarInventory;

	public OldJarCrawler(CurrentJarInventory currentJarInventory) {
		super();
		this.currentJarInventory = currentJarInventory;
	}

	public void crawl(URL urlToJar) throws IOException {
		PreviousJarVisitor visitor = new PreviousJarVisitor(currentJarInventory);

		UrlWalker urlWalker = new UrlWalker(visitor);
		urlWalker.visitJar(urlToJar);
	}
}
