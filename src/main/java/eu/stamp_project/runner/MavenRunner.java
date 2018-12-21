package eu.stamp_project.runner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenRunner {

	static Logger log = LoggerFactory.getLogger(MavenRunner.class);

	public static void compileWithoutTests(File projectFolder) throws IOException {
		log.debug("Compiling project with Maven");

		try {
			executeProcess(projectFolder, "mvn", "clean", "compile", "-DskipTests");
			log.debug("Project compiled with Maven");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dspotAmplifyUnitTests(File projectFolder, String propertiesFile) throws IOException {
		// mvn eu.stamp-project:dspot-maven:amplify-unit-tests -Dpath-to-properties=dhell.dspot -Damplifiers=TestDataMutator -Dtest-criterion=JacocoCoverageSelector -Diteration=1'
		log.debug("Executing DSPot");

		try {
			executeProcess(projectFolder, "mvn", "eu.stamp-project:dspot-maven:amplify-unit-tests",
					"-Dpath-to-properties=" + propertiesFile, "-Damplifiers=TestDataMutator",
					"-Dtest-criterion=JacocoCoverageSelector", "-Diteration=1");

			log.debug("DSPot executed successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void executeProcess(File workDir, String... command) throws InterruptedException, IOException {
		ProcessBuilder builder = new ProcessBuilder(command);

		builder.directory(workDir.getAbsoluteFile());
		builder.redirectErrorStream(true);

		Process process = builder.start();

		Scanner s = new Scanner(process.getInputStream());
		StringBuilder text = new StringBuilder();
		while (s.hasNextLine()) {
			text.append(s.nextLine());
			text.append("\n");
		}
		s.close();

		int result = process.waitFor();

		log.debug("Process exited with result {} and output {} ", result, text);
	}

}