package com.chromediopside.service;

import static org.junit.Assert.*;

import com.chromediopside.datatransfer.LoginForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ErrorServiceTest {

  @Autowired
  private ErrorService errorService;

  @Test
  public void missingValueUsername() {
    LoginForm loginForm = new LoginForm();
    loginForm.setAccessToken("4cc355T0k3n");

    String actualMissingValue = errorService.missingValues(loginForm);
    assertEquals("username!", actualMissingValue);
  }

  @Test
  public void missingValueAccessToken() {
    LoginForm loginForm = new LoginForm();
    loginForm.setUsername("us3rn4m3");

    String actualMissingValue = errorService.missingValues(loginForm);
    assertEquals("accessToken!", actualMissingValue);
  }

  @Test
  public void missingUsernameAndAccessToken() {
    LoginForm loginForm = new LoginForm();

    String actualMissingValues = errorService.missingValues(loginForm);
    assertEquals("accessToken, username!", actualMissingValues);
  }


}
