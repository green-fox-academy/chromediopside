package com.chromediopside.service;

import java.util.UUID;

public class GiTinderUserService {

  public String generateAppToken() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
  }
}
