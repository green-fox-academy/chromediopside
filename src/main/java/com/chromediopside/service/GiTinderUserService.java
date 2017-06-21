package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiTinderUserService {

  private final String errorMessage = "No Such AppToken in the Database";
  UserRepository userRepo;

  @Autowired
  public GiTinderUserService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public GiTinderUserService() {
  }

  public void setUserRepo(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public String generateAppToken() {
    String appToken;
    do {
      appToken = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    } while (userRepo.findByAppToken(appToken) != null);
    return appToken;
  }

  public Object getUserObjectByAppToken(String appToken) {
    if (userRepo.findByAppToken(appToken) != null) {
      GiTinderUser giTinderUser = userRepo.findByAppToken(appToken);
      return giTinderUser;
    }
    return new ErrorResponse(errorMessage);
  }

  public String createAndSaveUser(LoginForm loginForm) {
    String appToken = generateAppToken();
    String username = loginForm.getUsername();
    String accessToken = loginForm.getAccessToken();
    userRepo.save(new GiTinderUser(username, accessToken, appToken));
    return appToken;
  }
}
