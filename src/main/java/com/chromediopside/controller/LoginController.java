package com.chromediopside.controller;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.service.LoginService;
import com.chromediopside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;

  @Autowired
  UserService userService;

  @PostMapping(value = "/login")
  public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
    return loginService.login(loginForm);
  }
}
