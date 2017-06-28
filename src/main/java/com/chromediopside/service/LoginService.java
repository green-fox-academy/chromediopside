package com.chromediopside.service;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.datatransfer.TokenResponse;
import java.io.IOException;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class LoginService {

  private GiTinderUserService userService;
  private ErrorService errorService;

  @Autowired
  public LoginService(GiTinderUserService userService, ErrorService errorService) {
    this.userService = userService;
    this.errorService = errorService;
  }

  public ResponseEntity<?> login(LoginForm loginForm,
          BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return errorService.fieldErrors(bindingResult);
    }
    String appToken = userService.createAndSaveUser(loginForm);
    TokenResponse tokenResponse = new TokenResponse(appToken);
    ResponseEntity responseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    return responseEntity;
  }

  public boolean loginFormContainsValidAccessToken(LoginForm loginForm) {
    GitHubClient gitHubClient =  GitHubClientSevice.setUpGitHubClient(loginForm.getAccessToken());
    UserService userService = new UserService(gitHubClient);
    try {
      User user = userService.getUser();
      if (user.getLogin().equals(loginForm.getUsername())) {
        return true;
      }
    } catch (IOException e) {
      System.out.println(GitHubClientSevice.getGetRequestIoerror());
    }
    return false;
  }
}
