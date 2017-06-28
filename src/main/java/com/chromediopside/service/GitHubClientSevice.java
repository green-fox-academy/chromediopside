package com.chromediopside.service;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.stereotype.Service;

@Service
public class GitHubClientSevice {

  public static GitHubClient setUpGitHubClient(String accessToken) {
    GitHubClient gitHubClient = new GitHubClient();
    gitHubClient.setOAuth2Token(accessToken);
    return gitHubClient;
  }
}
