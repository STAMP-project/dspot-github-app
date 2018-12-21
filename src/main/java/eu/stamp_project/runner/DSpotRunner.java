package eu.stamp_project.runner;

import java.io.File;
import java.io.IOException;

import eu.stamp_project.Main;
import eu.stamp_project.program.InputConfiguration;

public class DSpotRunner {

	public static void runDspot(File repositoryFolder) throws IOException {
		//dove prendo le properties? Da file?
		// oppure posso configurarle dalla GitHub App quando la installo nel repository? ...da indagare!

		executeMaven(repositoryFolder.getCanonicalPath(), repositoryFolder+"/dhell.dspot");
	}

	private static void executeMaven(String projectFolder, String propertiesFile) throws IOException {
		// TODO l'esecuzione di Maven da linea di comando
		// devo eseguire DSPOT come nel seguente comando:
		// sh 'mvn eu.stamp-project:dspot-maven:amplify-unit-tests -Dpath-to-properties=dhell.dspot -Damplifiers=TestDataMutator -Dtest-criterion=JacocoCoverageSelector -Diteration=1'

		Runtime.getRuntime()
				.exec("mvn eu.stamp-project:dspot-maven:amplify-unit-tests "
						+ " -f " + projectFolder + "/pom.xml "
						+ " -Dpath-to-properties=" + propertiesFile
						+ " -Damplifiers=TestDataMutator -Dtest-criterion=JacocoCoverageSelector -Diteration=1");

		//MavenCli cli = new MavenCli();
		//cli.doMain(new String[]{"clean", "install"}, "project_dir", System.out, System.out);
	}

	private void executeJar() {
		//echo "java -jar $dspotJar --path-to-properties $dspotPropertiesFile -i 1 --test eu.stampproject.examples.dhell.HelloAppTest -a MethodAdd --verbose" 2>&1 | tee $traceFile

	}


	private void executeMain() {
		InputConfiguration ic = InputConfiguration.initialize("src/test/resources/test-projects/test-projects.properties");

		try {
			Main.run(ic);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
