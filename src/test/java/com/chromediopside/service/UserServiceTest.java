package com.chromediopside.service;

import static org.junit.Assert.*;

import com.chromediopside.model.GiTinderUser;
import com.chromediopside.repository.UserRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

  @Test
  public void getUserObjectByAppTokenTest() {
    GiTinderUser expectedGiTinderUser = new GiTinderUser("testa", "a23456789101112a", "aa345678910111aa");

    UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    Mockito.when(mockUserRepo.findByAppToken("aa345678910111aa")).thenReturn(expectedGiTinderUser);

    GiTinderUserService userService = new GiTinderUserService(mockUserRepo);
    Object actualGiTinderUser = userService.getUserObjectByAppToken("aa345678910111aa");

    assertEquals(expectedGiTinderUser.toString(), actualGiTinderUser.toString());
  }
}
