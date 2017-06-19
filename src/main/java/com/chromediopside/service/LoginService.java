package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.datatransfer.TokenResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

  public ResponseEntity<?> login(LoginForm loginForm) {
    if (missingValues(loginForm).equals("")) {
      return new ResponseEntity<>(new TokenResponse("abc123"), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ErrorResponse("Missing parameter(s): "
        + missingValues(loginForm) + "!"), HttpStatus.BAD_REQUEST);
  }


  private String missingValues(LoginForm loginForm) {
    List<String> missingValues = new ArrayList<>();
    if (loginForm.getUsername() == null) {
      missingValues.add("username");
    }
    if (loginForm.getAccessToken() == null) {
      missingValues.add("accessToken");
    }
    return String.join(", ", missingValues);
  }

}
