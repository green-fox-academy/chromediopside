package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.datatransfer.LoginForm;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

  private final String errorMessage = "No Such AppToken in the Database";

  @Autowired
  UserRepository userRepo;

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

  public void createAndSaveUser(LoginForm loginForm, String appToken) {
    String username = loginForm.getUsername();
    String accessToken = loginForm.getAccessToken();
    userRepo.save(new GiTinderUser(username, accessToken, appToken));
  }
}
