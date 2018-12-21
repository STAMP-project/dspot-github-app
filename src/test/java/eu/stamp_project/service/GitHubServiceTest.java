package eu.stamp_project.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class GitHubServiceTest {

	Logger log = LoggerFactory.getLogger(GitHubServiceTest.class);

	@Autowired
	GitHubService service;

	@Before
	public void before() {
	}

	@Test
	public void createPullRequestTest() {
		try {
			String owner = "luandrea";
			String repoName = "testrepo-github-app";

			service.createPullRequest(repoName, owner, "Pull request title", "Pull request body", "nuovo-branch",
					"master");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}