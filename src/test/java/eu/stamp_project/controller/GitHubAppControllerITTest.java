package eu.stamp_project.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import eu.stamp_project.service.GitHubService;
import eu.stamp_project.service.JGitService;
import eu.stamp_project.utility.ConfigurationBean;

@RunWith(SpringRunner.class)
//@WebMvcTest(value = GitHubAppController.class)
@SpringBootTest(classes = { GitHubAppController.class, GitHubService.class, JGitService.class,
		ConfigurationBean.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class GitHubAppControllerITTest {

	Logger log = LoggerFactory.getLogger(GitHubAppControllerITTest.class);

	@Autowired
	private MockMvc mockMvc;

	String pushEventJson = "{\"ref\":\"refs/heads/jenkins_develop\",\"before\":\"f984c4aa5e798a0ae2eb3b27f35c6b6077207355\",\"after\":\"f0d13b9d52459ae99e7df64552f7c07a91ebf04b\",\"created\":false,\"deleted\":false,\"forced\":false,\"base_ref\":null,\"compare\":\"https://github.com/luandrea/dhell/compare/f984c4aa5e79...f0d13b9d5245\",\"commits\":[{\"id\":\"f0d13b9d52459ae99e7df64552f7c07a91ebf04b\",\"tree_id\":\"fd9de30310f3e020813b116723cd5679af4eef8d\",\"distinct\":true,\"message\":\"Update README.md\",\"timestamp\":\"2018-12-21T11:31:22+01:00\",\"url\":\"https://github.com/luandrea/dhell/commit/f0d13b9d52459ae99e7df64552f7c07a91ebf04b\",\"author\":{\"name\":\"Luca Andreatta\",\"email\":\"luca.andreatta@gmail.com\",\"username\":\"luandrea\"},\"committer\":{\"name\":\"GitHub\",\"email\":\"noreply@github.com\",\"username\":\"web-flow\"},\"added\":[],\"removed\":[],\"modified\":[\"README.md\"]}],\"head_commit\":{\"id\":\"f0d13b9d52459ae99e7df64552f7c07a91ebf04b\",\"tree_id\":\"fd9de30310f3e020813b116723cd5679af4eef8d\",\"distinct\":true,\"message\":\"Update README.md\",\"timestamp\":\"2018-12-21T11:31:22+01:00\",\"url\":\"https://github.com/luandrea/dhell/commit/f0d13b9d52459ae99e7df64552f7c07a91ebf04b\",\"author\":{\"name\":\"Luca Andreatta\",\"email\":\"luca.andreatta@gmail.com\",\"username\":\"luandrea\"},\"committer\":{\"name\":\"GitHub\",\"email\":\"noreply@github.com\",\"username\":\"web-flow\"},\"added\":[],\"removed\":[],\"modified\":[\"README.md\"]},\"repository\":{\"id\":162590961,\"node_id\":\"MDEwOlJlcG9zaXRvcnkxNjI1OTA5NjE=\",\"name\":\"dhell\",\"full_name\":\"luandrea/dhell\",\"private\":false,\"owner\":{\"name\":\"luandrea\",\"email\":\"luca.andreatta@gmail.com\",\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/luandrea/dhell\",\"description\":\"Dummy HELLo world\",\"fork\":true,\"url\":\"https://github.com/luandrea/dhell\",\"forks_url\":\"https://api.github.com/repos/luandrea/dhell/forks\",\"keys_url\":\"https://api.github.com/repos/luandrea/dhell/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/luandrea/dhell/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/luandrea/dhell/teams\",\"hooks_url\":\"https://api.github.com/repos/luandrea/dhell/hooks\",\"issue_events_url\":\"https://api.github.com/repos/luandrea/dhell/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/luandrea/dhell/events\",\"assignees_url\":\"https://api.github.com/repos/luandrea/dhell/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/luandrea/dhell/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/luandrea/dhell/tags\",\"blobs_url\":\"https://api.github.com/repos/luandrea/dhell/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/luandrea/dhell/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/luandrea/dhell/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/luandrea/dhell/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/luandrea/dhell/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/luandrea/dhell/languages\",\"stargazers_url\":\"https://api.github.com/repos/luandrea/dhell/stargazers\",\"contributors_url\":\"https://api.github.com/repos/luandrea/dhell/contributors\",\"subscribers_url\":\"https://api.github.com/repos/luandrea/dhell/subscribers\",\"subscription_url\":\"https://api.github.com/repos/luandrea/dhell/subscription\",\"commits_url\":\"https://api.github.com/repos/luandrea/dhell/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/luandrea/dhell/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/luandrea/dhell/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/luandrea/dhell/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/luandrea/dhell/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/luandrea/dhell/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/luandrea/dhell/merges\",\"archive_url\":\"https://api.github.com/repos/luandrea/dhell/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/luandrea/dhell/downloads\",\"issues_url\":\"https://api.github.com/repos/luandrea/dhell/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/luandrea/dhell/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/luandrea/dhell/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/luandrea/dhell/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/luandrea/dhell/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/luandrea/dhell/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/luandrea/dhell/deployments\",\"created_at\":1545316445,\"updated_at\":\"2018-12-21T10:29:17Z\",\"pushed_at\":1545388283,\"git_url\":\"git://github.com/luandrea/dhell.git\",\"ssh_url\":\"git@github.com:luandrea/dhell.git\",\"clone_url\":\"https://github.com/luandrea/dhell.git\",\"svn_url\":\"https://github.com/luandrea/dhell\",\"homepage\":null,\"size\":81,\"stargazers_count\":0,\"watchers_count\":0,\"language\":\"Java\",\"has_issues\":false,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"open_issues_count\":0,\"license\":null,\"forks\":0,\"open_issues\":0,\"watchers\":0,\"default_branch\":\"master\",\"stargazers\":0,\"master_branch\":\"master\"},\"pusher\":{\"name\":\"luandrea\",\"email\":\"luca.andreatta@gmail.com\"},\"sender\":{\"login\":\"luandrea\",\"id\":1321508,\"node_id\":\"MDQ6VXNlcjEzMjE1MDg=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1321508?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/luandrea\",\"html_url\":\"https://github.com/luandrea\",\"followers_url\":\"https://api.github.com/users/luandrea/followers\",\"following_url\":\"https://api.github.com/users/luandrea/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/luandrea/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/luandrea/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/luandrea/subscriptions\",\"organizations_url\":\"https://api.github.com/users/luandrea/orgs\",\"repos_url\":\"https://api.github.com/users/luandrea/repos\",\"events_url\":\"https://api.github.com/users/luandrea/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/luandrea/received_events\",\"type\":\"User\",\"site_admin\":false},\"installation\":{\"id\":449175,\"node_id\":\"MDIzOkludGVncmF0aW9uSW5zdGFsbGF0aW9uNDQ5MTc1\"}}";
	String arrayPushEvent = "[{\"id\":\"40cc77e2a3b315c5fafc90151bdaf08eef9c442c\",\"tree_id\":\"68f6572a71291bd3e71785d0f226d37f81a6f446\",\"distinct\":true,\"message\":\"Update README.md\",\"timestamp\":\"2018-12-21T15:55:10+01:00\",\"url\":\"https://github.com/luandrea/dhell/commit/40cc77e2a3b315c5fafc90151bdaf08eef9c442c\",\"author\":{\"name\":\"Luca Andreatta\",\"email\":\"luca.andreatta@gmail.com\",\"username\":\"luandrea\"},\"committer\":{\"name\":\"GitHub\",\"email\":\"noreply@github.com\",\"username\":\"web-flow\"},\"added\":[],\"removed\":[],\"modified\":[\"README.md\"]}]";

	@Test
	public void handlePipelineTest() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dspot-github-app")
				.header("X-GitHub-Event", "push")
				.accept(MediaType.APPLICATION_JSON)
				.content(pushEventJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		//JSONAssert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		//JSONAssert.assertEquals("http://localhost/students/Student1/courses/1",
		//		response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void arrayPushEventTest() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dspot-github-app")
				.header("X-GitHub-Event", "push")
				.accept(MediaType.APPLICATION_JSON)
				.content(arrayPushEvent)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		//JSONAssert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		//JSONAssert.assertEquals("http://localhost/students/Student1/courses/1",
		//		response.getHeader(HttpHeaders.LOCATION));
	}
}
