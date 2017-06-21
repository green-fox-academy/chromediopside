package com.chromediopside.service;

import static org.junit.Assert.*;

import com.chromediopside.mockbuilder.MockUserBuilder;
import com.chromediopside.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

  @Autowired
  private GiTinderUserService userService;
  @Autowired
  private MockUserBuilder mockUserBuilder;

  @Test
  public void getUserObjectByAppTokenTest() {
    UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    Mockito.when(mockUserRepo.findByAppToken("aa345678910111aa")).thenReturn(mockUserBuilder.build());
    userService.setUserRepo(mockUserRepo);
    Object actualGiTinderUser = userService.getUserObjectByAppToken("aa345678910111aa");

    assertEquals(mockUserBuilder.build(), actualGiTinderUser);
  }
}
