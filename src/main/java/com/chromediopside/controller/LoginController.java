package com.chromediopside.controller;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.repository.ProfileRepository;
import com.chromediopside.service.GiTinderUserService;
import com.chromediopside.service.LoginService;
import com.chromediopside.service.ErrorService;
import com.chromediopside.service.ProfileService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  private LoginService loginService;
  private GiTinderUserService userService;
  private ErrorService errorService;
  private ProfileService profileService;

  @Autowired
  public LoginController(LoginService loginService,
      GiTinderUserService userService, ErrorService errorService, ProfileService profileService) {
    this.loginService = loginService;
    this.userService = userService;
    this.errorService = errorService;
    this.profileService = profileService;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm,
          BindingResult bindingResult) {


    profileService.fetchAndSaveProfileOnLogin(loginForm);
    return loginService.login(loginForm, bindingResult);
  }
}
