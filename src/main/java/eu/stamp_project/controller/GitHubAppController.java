package eu.stamp_project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.stamp_project.runner.MavenRunner;
import eu.stamp_project.service.GitHubService;
import eu.stamp_project.service.JGitService;
import eu.stamp_project.utility.FileUtility;

@RestController
public class GitHubAppController {

	Logger log = LoggerFactory.getLogger(GitHubAppController.class);

	@Autowired
	private JGitService gitService;

	@Autowired
	private GitHubService githubService;

	@RequestMapping("/test")
    public String greeting() {
        return "Test Rest Service";
	}

	@PostMapping(value = "/dspot-github-app")
	public Map<String, String> getPullRequestFullBody(HttpServletRequest request,
			@RequestHeader(value = "X-GitHub-Event", defaultValue = "") String eventType) {
		HashMap<String, String> response = new HashMap<>();

		try {
			log.debug("'" + eventType + "' Event received");

			// TODO error if push event is like this:
			// [{"id":"40cc77e2a3b315c5fafc90151bdaf08eef9c442c","tree_id":"68f6572a71291bd3e71785d0f226d37f81a6f446","distinct":true,"message":"Update README.md","timestamp":"2018-12-21T15:55:10+01:00","url":"https://github.com/luandrea/dhell/commit/40cc77e2a3b315c5fafc90151bdaf08eef9c442c","author":{"name":"Luca Andreatta","email":"luca.andreatta@gmail.com","username":"luandrea"},"committer":{"name":"GitHub","email":"noreply@github.com","username":"web-flow"},"added":[],"removed":[],"modified":["README.md"]}]
			// ex.: JSONArray jsonarray = new JSONArray(jsonStr);

			// get JSON Object from request
			JsonObject jsonObject = getJSonObjectFromRequest(request);

			// get repository information
			String repositoryName = jsonObject.get("repository").getAsJsonObject().get("name").getAsString();
			String repositoryURL = jsonObject.get("repository").getAsJsonObject().get("url").getAsString();
			String repositoryOwner = jsonObject.get("repository").getAsJsonObject().get("owner").getAsJsonObject().get("name").getAsString();

			// push event
			if (eventType.equals("push")) {

				// get commit information
				String branchRef =  jsonObject.get("ref").getAsString();
				String branchEvent =  branchRef.substring(branchRef.lastIndexOf("/")+1);
				String commitId = jsonObject.get("commits").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();

				handlePipeline(branchEvent, repositoryName, repositoryURL, repositoryOwner, commitId);
				response.put("message", "Pull request created with amplified tests!");

			} else {
				response.put("message", "This event action is not a target.");
			}

		} catch (Exception e) {
			log.error("Error parsing event", e);
		}

		return response;
	}

	public void handlePipeline(String branchEvent, String repositoryName, String repositoryURL, String repositoryOwner, String commitId) throws Exception {
		// example: https://github.com/STAMP-project/dhell/blob/master/Jenkinsfile

		// checkout project from GitHub
		File repoFolder = gitService.cloneRepository(repositoryURL);

		// checkout branch of the push
		gitService.checkoutBranch(repoFolder, branchEvent);

		// compile project
		MavenRunner.compileWithoutTests(repoFolder);

		// execute DSpot to amplify Tests
		// TODO remove configuration file reference, find it in a better way
		MavenRunner.dspotAmplifyUnitTests(repoFolder, "dhell.dspot");

		// copy new dspot tests to src folder
		File source = new File(repoFolder.getAbsolutePath()+"/target/dspot/output");
		File dest = new File(repoFolder.getAbsolutePath()+"/src/test/java/");
		FileUtility.copyDirectory(source, dest);
		log.debug("New Tests added to source folder");

		// create new branch
		String branch = "dspot-branch-"+commitId;
		gitService.createNewBranch(repoFolder, branch, true);

		// commit new tests
		gitService.commitAll(repoFolder, "added tests");

		// push
		gitService.push(repoFolder, branch);

		// pull request from branch to master
		githubService.createPullRequest(repositoryName, repositoryOwner, "DSpot Amplify",
				"Dspot Amplify pull request from commit "+commitId+" on " + branch, branch, "master");
	}


	private static JsonObject getJSonObjectFromRequest(HttpServletRequest request) throws IOException, JSONException {
		JsonObject jsonObject = null;

		// get body from request
		String body = getBody(request);

		Gson gson = new Gson();
		if (body.startsWith("[")) {
			JsonArray entries = (JsonArray) new JsonParser().parse(body);
			jsonObject = ((JsonObject)entries.get(0));

//			JSONArray jsonarray = new JSONArray(body);
//			jsonObject = (JsonObject) jsonarray.get(0);
		} else {
			jsonObject = gson.fromJson(body, JsonElement.class).getAsJsonObject();
		}

		return jsonObject;
	}

	/**
	 * Read body from request
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private static String getBody(HttpServletRequest request) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();

		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}

		return buffer.toString();
	}

}