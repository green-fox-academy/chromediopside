package com.chromediopside.service;

import com.chromediopside.datatransfer.ErrorResponse;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

  private final String errorMessage = "No Such AppToken in the Database";

  @Autowired
  UserRepository userRepo;

  public String generateAppToken() {
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
  }

  public Object getUserObjectByAppToken(String appToken) {

    if (userRepo.findByAppTokenExists(appToken)) {
      GiTinderUser giTinderUser = userRepo.findByAppToken(appToken);
      return giTinderUser;
    }
    return new ErrorResponse(errorMessage);
  }

}
