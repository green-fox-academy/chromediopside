package com.chromediopside.service;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.springframework.stereotype.Service;

@Service
public class GitHubClientSevice {

  private static final String GET_REQUEST_IOERROR
      = "Some GitHub data of this user is not available for this token!";

  public static String getGetRequestIoerror() {
    return GET_REQUEST_IOERROR;
  }

  public static GitHubClient setUpGitHubClient(String accessToken) {
    GitHubClient gitHubClient = new GitHubClient();
    gitHubClient.setOAuth2Token(accessToken);
    return gitHubClient;
  }
}
