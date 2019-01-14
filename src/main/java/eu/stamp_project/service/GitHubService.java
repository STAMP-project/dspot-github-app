package eu.stamp_project.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eu.stamp_project.utility.ConfigurationBean;

@Service
public class GitHubService {
	Logger log = LoggerFactory.getLogger(GitHubService.class);

	private GitHubClient client;

	public GitHubService(ConfigurationBean configuration) {
		super();

		client = new GitHubClient().setCredentials(configuration.getGithubUsername(),
				configuration.getGithubPassword());

		if (configuration.getProxyHost() != null && configuration.getProxyPort() != null) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP,
					new InetSocketAddress(configuration.getProxyHost(), configuration.getProxyPort()));
			client.setProxy(proxy);
		}
	}

	public void createPullRequest(String repositoryName, String repositoryOwner, String pullRequestTitle,
			String pullRequestBody, String branchSource, String branchDestination) throws IOException {
		log.debug("Creating pull request");

		RepositoryService repoService = new RepositoryService(client);
		Repository repository = repoService.getRepository(repositoryOwner, repositoryName);

		PullRequestService service = new PullRequestService(client);

		PullRequest request = new PullRequest();
		request.setTitle(pullRequestTitle);
		request.setBody(pullRequestBody);

		request.setHead(new PullRequestMarker().setRef(branchSource).setLabel(branchSource));
		request.setBase(new PullRequestMarker().setRef(branchDestination).setLabel(branchDestination));

		service.createPullRequest(repository, request);
		log.debug("Pull request created");
	}

}
