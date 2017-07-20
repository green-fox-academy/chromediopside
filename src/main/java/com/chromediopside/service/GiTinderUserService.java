package com.chromediopside.service;

import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiTinderUserService {

  private UserRepository userRepo;

  @Autowired
  public GiTinderUserService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public GiTinderUserService() {
  }

  public String generateAppToken() {
    String appToken;
    do {
      appToken = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    } while (userRepo.findByAppToken(appToken) != null);
    return appToken;
  }

  public GiTinderUser getUserByAppToken(String appToken) {
    GiTinderUser giTinderUser = userRepo.findByAppToken(appToken);
    return giTinderUser;
  }

  public String createAndSaveUser(LoginForm loginForm) {
    String appToken = generateAppToken();
    String username = loginForm.getUsername();
    String accessToken = loginForm.getAccessToken();
    GiTinderUser user = new GiTinderUser(username, accessToken, appToken);
    userRepo.save(user);
    return appToken;
  }

  public boolean validAppToken(String appToken) {
    return userRepo.findByAppToken(appToken) != null;
  }
}
