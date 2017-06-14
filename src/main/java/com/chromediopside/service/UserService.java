package com.chromediopside.service;

import java.util.UUID;

public class UserService {

  public String generateAppToken() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
  }
}
