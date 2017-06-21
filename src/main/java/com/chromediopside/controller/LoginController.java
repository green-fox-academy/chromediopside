package com.chromediopside.controller;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.service.LoginService;
import com.chromediopside.service.UserService;
import com.chromediopside.service.ValidatorService;
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
  private UserService userService;
  private ValidatorService validatorService;

  @Autowired
  public LoginController(LoginService loginService, UserService userService) {
    this.loginService = loginService;
    this.userService = userService;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return validatorService.fieldErrors(bindingResult);
    }
    String appToken = userService.generateAppToken();
    userService.createAndSaveUser(loginForm, appToken);
    return loginService.loginResponse(appToken);
  }
}
