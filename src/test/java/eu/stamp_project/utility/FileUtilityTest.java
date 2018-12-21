package eu.stamp_project.utility;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.stamp_project.controller.GitHubAppControllerITTest;

public class FileUtilityTest {

	Logger log = LoggerFactory.getLogger(GitHubAppControllerITTest.class);

	@Test
	public void copyFolderTest() {

		String repoFolderPath = "/tmp/1545393228401-0";

		try {

			File source = new File(repoFolderPath + "/target/dspot/output");
			File dest = new File(repoFolderPath + "/src/test/java/");
			FileUtility.copyDirectory(source, dest);

			log.debug("New Tests added to source folder");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
