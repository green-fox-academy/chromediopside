package com.chromediopside.controller;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.service.LoginService;
import com.chromediopside.service.ErrorService;
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
  private ErrorService errorService;

  @Autowired
  public LoginController(
      LoginService loginService,
      UserService userService,
      ErrorService errorService) {
    this.loginService = loginService;
    this.userService = userService;
    this.errorService = errorService;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm,
          BindingResult bindingResult) {
    return loginService.login(loginForm, bindingResult);
  }
}
