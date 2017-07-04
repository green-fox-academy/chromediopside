package com.chromediopside.service;

import static org.junit.Assert.*;

import com.chromediopside.mockbuilder.MockUserBuilder;
import com.chromediopside.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

  @MockBean
  private UserRepository userRepository;
  @Autowired
  private GiTinderUserService userService;
  @Autowired
  private MockUserBuilder mockUserBuilder;

  @Test
  public void getUserObjectByAppTokenTest() {
    Mockito.when(userRepository.findByAppToken("aa345678910111aa")).thenReturn(mockUserBuilder.build());
    Object actualGiTinderUser = userService.getUserByAppToken("aa345678910111aa");

    assertEquals(mockUserBuilder.build(), actualGiTinderUser);
  }

  @Test
  public void validAppTokenTest() {

    Mockito.when(userRepository.findByAppToken("v4lid4ppT0k3n")).thenReturn(mockUserBuilder.build());

    String appToken = "v4lid4ppT0k3n";
    boolean mockAppTokenChecker = userService.validAppToken(appToken);
    assertEquals(true, mockAppTokenChecker);
  }

  @Test
  public void inValidAppTokenTest() {

    Mockito.when(userRepository.findByAppToken("inV4lid4ppT0k3n")).thenReturn(mockUserBuilder.build());

    String appToken = "";
    boolean mockAppTokenChecker = userService.validAppToken(appToken);
    assertEquals(false, mockAppTokenChecker);
  }


}
