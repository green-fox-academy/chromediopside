package com.chromediopside.controller;

import com.chromediopside.model.GiTinderProfile;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  @RequestMapping("/profile")
  public GiTinderProfile getProfile(@RequestHeader(name = "X-GiTinder-token") String token) {

    GiTinderProfile mockProfile = new GiTinderProfile();
    mockProfile.setLogin("kondfox");
    mockProfile.setAvatarUrl("https://avatars1.githubusercontent.com/u/26329189?v=3");
    mockProfile.setRepos("repo1;repo2;repo3");

    return mockProfile;
  }
}
