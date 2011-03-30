package fitlibrary.log;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

public class ConfigureLoggingThroughFiles {
	public static void configure() {
		configure("");
	}

	public static void configure(String diry) {
		PropertyConfigurator propertyConfigurator = new PropertyConfigurator();
		if (new File(diry + "FitLibraryLogger.properties").exists()) {
			try {
				propertyConfigurator.doConfigure(diry + "FitLibraryLogger.properties",
						FitLibraryLogger.getOwnHierarchy());
			} catch (Exception e) {
				System.err.println("Problem with FitLibraryLogger.properties " + e.getMessage());
			}
		}
		if (new File(diry + "FixturingLogger.properties").exists()) {
			try {
				propertyConfigurator
						.doConfigure(diry + "FixturingLogger.properties", FixturingLogger.getOwnHierarchy());
			} catch (Exception e) {
				System.err.println("Problem with FixturingLogger.properties " + e.getMessage());
			}
		}
		if (new File(diry + "log4j.properties").exists()) {
			try {
				PropertyConfigurator.configure(diry + "log4j.properties");
			} catch (Exception e) {
				System.err.println("Problem with log4j.properties " + e.getMessage());
			}
		}
	}
}
