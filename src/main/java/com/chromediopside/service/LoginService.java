package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.datatransfer.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginService {
  
  public ResponseEntity<?> login(LoginForm loginForm) {
    String missingValues = "";
    boolean complete = true;
    if (loginForm.getUsername() == null) {
      missingValues += "username";
      complete = false;
    }
    if (loginForm.getAccessToken() == null) {
      if (!complete) {
        missingValues += ", ";
      }
      missingValues += "accessToken";
      complete = false;
    }
    if (complete) {
      return new ResponseEntity<>(new TokenResponse("abc123"), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ErrorResponse("Missing parameter(s): " + missingValues + "!"),
          HttpStatus.BAD_REQUEST);
  }
}
