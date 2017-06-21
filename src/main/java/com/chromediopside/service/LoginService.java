package com.chromediopside.service;

import com.chromediopside.datatransfer.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private TokenResponse tokenResponse;

  @Autowired
  public LoginService(TokenResponse tokenResponse) {
    this.tokenResponse = tokenResponse;
  }

  public ResponseEntity<?> loginResponse(String appToken) {
    tokenResponse.setToken(appToken);
    return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
  }
}
