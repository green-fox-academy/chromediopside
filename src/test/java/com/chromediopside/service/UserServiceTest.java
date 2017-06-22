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
    Object actualGiTinderUser = userService.getUserObjectByAppToken("aa345678910111aa");

    assertEquals(mockUserBuilder.build(), actualGiTinderUser);
  }
}