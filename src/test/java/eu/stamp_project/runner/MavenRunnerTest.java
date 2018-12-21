package eu.stamp_project.runner;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MavenRunnerTest {

	Logger log = LoggerFactory.getLogger(MavenRunnerTest.class);

	@Test
	public void compileWithoutTestsTest() {
		try {

			MavenRunner.compileWithoutTests(new File("/tmp/stamp-test/dhell"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void dspotAmplifyUnitTestsTest() {
		try {

			MavenRunner.dspotAmplifyUnitTests(new File("/tmp/stamp-test/dhell"), "dhell.dspot");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
