package com.chromediopside.controller;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.LoginService;
import com.chromediopside.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  @PostMapping(value = "/login")
  public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) {
    if (loginForm.getUsername() == null || loginForm.getAccessToken() == null) {
      return new ResponseEntity<>(errorService.fieldErrors(loginForm), HttpStatus.BAD_REQUEST);
    }
    if (loginService.loginFormContainsValidAccessToken(loginForm)) {
      profileService.fetchAndSaveProfileOnLogin(loginForm);
      return new ResponseEntity<>(loginService.login(loginForm), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(errorService.unauthorizedRequestError(), HttpStatus.UNAUTHORIZED);
    }
  }
}
