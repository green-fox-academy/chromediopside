package com.chromediopside.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GiTinderUser {

  @Id
  String username;
  String accessToken;
  String appToken;

}
