package com.chromediopside.controller;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by peter on 2017.06.12..
 */
@RestController
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
      return loginService.login(loginForm);
    }
}
