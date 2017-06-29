package com.chromediopside.controller;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.service.LoginService;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.ProfileService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private LoginService loginService;
  private ErrorService errorService;
  private ProfileService profileService;

  @Autowired
  public LoginController(
      LoginService loginService,
      ErrorService errorService,
      ProfileService profileService) {
    this.loginService = loginService;
    this.errorService = errorService;
    this.profileService = profileService;
  }

  @CrossOrigin("*")
  @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm,
          BindingResult bindingResult) {
    if(loginService.loginFormContainsValidAccessToken(loginForm)) {
      profileService.fetchAndSaveProfileOnLogin(loginForm);
      return loginService.login(loginForm, bindingResult);
    } else {
      return errorService.unauthorizedRequestError();
    }
  }

  public LoginService getLoginService() {
    return loginService;
  }

  public void setLoginService(LoginService loginService) {
    this.loginService = loginService;
  }

  public ErrorService getErrorService() {
    return errorService;
  }

  public void setErrorService(ErrorService errorService) {
    this.errorService = errorService;
  }

  public ProfileService getProfileService() {
    return profileService;
  }

  public void setProfileService(ProfileService profileService) {
    this.profileService = profileService;
  }
}
