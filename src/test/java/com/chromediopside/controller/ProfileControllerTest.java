package com.chromediopside.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.chromediopside.GitinderApplication;
import com.chromediopside.mockbuilder.MockProfileBuilder;
import com.chromediopside.mockbuilder.MockUserBuilder;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.repository.ProfileRepository;
import com.chromediopside.repository.SwipeRepository;
import com.chromediopside.repository.UserRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GitinderApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableWebMvc
public class ProfileControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private UserRepository userRepository;
  @MockBean
  private ProfileRepository profileRepository;
  @MockBean
  private SwipeRepository swipeRepository;
  @Autowired
  private MockUserBuilder mockUserBuilder;
  @Autowired
  private MockProfileBuilder mockProfileBuilder;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }


  @Test
  public void getProfileWithToken() throws Exception {

    Mockito.when(userRepository.findByAppToken("123")).thenReturn(mockUserBuilder.build());
    Mockito.when(userRepository.findByUserName("kondfox")).thenReturn(mockUserBuilder.build());
    Mockito.when(userRepository.findByUserNameAndAppToken("kondfox", "123")).thenReturn(mockUserBuilder.build());
    Mockito.when(profileRepository.existsByLogin("kondfox")).thenReturn(true);
    Mockito.when(profileRepository.findByLogin("kondfox")).thenReturn(mockProfileBuilder.setRefreshDate(new Timestamp(System.currentTimeMillis())).build());

    mockMvc.perform(get("/profile").header("X-GiTinder-token", "123"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$").value(hasKey("login")))
            .andExpect(jsonPath("$").value(hasKey("avatarUrl")))
            .andExpect(jsonPath("$").value(hasKey("repos")))
            .andExpect(jsonPath("$").value(hasKey("languagesList")))
            .andExpect(jsonPath("$.languagesList").value(
                    anyOf(any(Set.class), nullValue(Set.class))));
  }

  @Test
  public void getProfileWithoutToken() throws Exception {

    mockMvc.perform(get("/profile"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"error\","
                    + "\"message\" : \"Unauthorized request!\""
                    + "}"));
  }

  @Test
  public void getOtherProfileWithTokenAndName() throws Exception {

    Mockito.when(userRepository.findByAppToken("123")).thenReturn(mockUserBuilder.build());
    Mockito.when(userRepository.findByUserName("kondfox")).thenReturn(mockUserBuilder.build());
    Mockito.when(userRepository.findByUserNameAndAppToken("kondfox", "123")).thenReturn(mockUserBuilder.build());
    Mockito.when(profileRepository.existsByLogin("kondfox")).thenReturn(true);
    Mockito.when(profileRepository.findByLogin("kondfox")).thenReturn(mockProfileBuilder.setRefreshDate(new Timestamp(System.currentTimeMillis())).build());

    mockMvc.perform(get("/profiles/{username}", "kondfox").header("X-GiTinder-token", "123"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").value(hasKey("login")))
        .andExpect(jsonPath("$").value(hasKey("avatarUrl")))
        .andExpect(jsonPath("$").value(hasKey("repos")))
        .andExpect(jsonPath("$").value(hasKey("languagesList")))
        .andExpect(jsonPath("$.languagesList").value(
            anyOf(any(Set.class), nullValue(Set.class))));
  }

  @Test
  public void getOtherProfileWithoutToken() throws Exception {

    this.mockMvc.perform(get("/profiles/{username}", "kondfox"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("{"
            + "\"status\" : \"error\","
            + "\"message\" : \"Unauthorized request!\""
            + "}"));
  }

  @Test
  public void getOtherProfileWithUserMissingFromDatabase() throws Exception {

    Mockito.when(userRepository.findByAppToken("123")).thenReturn(mockUserBuilder.build());
    Mockito.when(userRepository.findByUserName("kondfox")).thenReturn(null);

    mockMvc.perform(get("/profiles/{username}", "kondfox").header("X-GiTinder-token", "123"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json("{"
            + "\"status\" : \"error\","
            + "\"message\" : \"No such user!\""
            + "}"));
  }

  @Test
  public void swipeTestWhenMatchIsNull() throws Exception {

    Mockito.when(userRepository.findByAppToken("123")).thenReturn(mockUserBuilder.build());

    mockMvc.perform(put("/profiles/{username}/{direction}", "kondfox", "right").header("X-GiTinder-token", "123"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"ok\","
                    + "\"message\" : \"success\","
                    + "\"match\" : null"
                    + "}"));
  }


  @Test
  public void listAvailablePagesWithoutToken() throws Exception {

    this.mockMvc.perform(get("/available"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"error\","
                    + "\"message\" : \"Unauthorized request!\""
                    + "}"));
  }

  @Test
  public void getPageWithToken() throws Exception {
    List<GiTinderProfile> testList = new ArrayList<>();
    testList.add(mockProfileBuilder.build());
    testList.add(mockProfileBuilder.build());

    Mockito.when(userRepository.findByAppToken("asd"))
            .thenReturn(mockUserBuilder.build());

    Mockito.when(profileRepository.listTensOrderByEntry("login", 0))
            .thenReturn(testList);
    Mockito.when(profileRepository.listTensOrderByEntry("avatar_url", 0))
            .thenReturn(testList);
    Mockito.when(profileRepository.listTensOrderByEntry("repos", 0))
            .thenReturn(testList);
    Mockito.when(profileRepository.listTensOrderByEntry("refresh_date", 0))
            .thenReturn(testList);
    Mockito.when(profileRepository.count()).thenReturn((long)testList.size());

    this.mockMvc.perform(get("/available").header("X-GiTinder-token", "asd"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$").value(hasKey("profiles")))
            .andExpect(jsonPath("$.profiles").value(
                    anyOf(any(List.class), nullValue(Set.class))))
            .andExpect(jsonPath("$").value(hasKey("count")))
            .andExpect(jsonPath("$").value(hasKey("all")));
  }

  @Test
  public void getPageWithTokenAndPageNumber() throws Exception {
    List<GiTinderProfile> testList = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
    testList.add(mockProfileBuilder.build());
    }
    testList.subList(10, 12);

    Mockito.when(userRepository.findByAppToken("asd"))
            .thenReturn(mockUserBuilder.build());

    Mockito.when(profileRepository.listTensOrderByEntry("login", 1))
            .thenReturn(testList);
    Mockito.when(profileRepository.listTensOrderByEntry("avatar_url", 1))
            .thenReturn(testList);
    Mockito.when(profileRepository.listTensOrderByEntry("repos", 1))
            .thenReturn(testList);
    Mockito.when(profileRepository.listTensOrderByEntry("refresh_date", 1))
            .thenReturn(testList);
    Mockito.when(profileRepository.count()).thenReturn((long)testList.size());

    this.mockMvc.perform(get("/available/{page}", 2).header("X-GiTinder-token", "asd"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$").value(hasKey("profiles")))
            .andExpect(jsonPath("$.profiles").value(
                    anyOf(any(List.class))))
            .andExpect(jsonPath("$").value(hasKey("count")))
            .andExpect(jsonPath("$").value(hasKey("all")));
  }

  @Test
  public void getPageWithTokenAndInvalidPageNumber() throws Exception {
    List<GiTinderProfile> testList = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
      testList.add(mockProfileBuilder.build());
    }

    Mockito.when(userRepository.findByAppToken("asd"))
            .thenReturn(mockUserBuilder.build());

    Mockito.when(profileRepository.listTensOrderByEntry("login", 2))
            .thenReturn(null);
    Mockito.when(profileRepository.listTensOrderByEntry("avatar_url", 2))
            .thenReturn(null);
    Mockito.when(profileRepository.listTensOrderByEntry("repos", 2))
            .thenReturn(null);
    Mockito.when(profileRepository.listTensOrderByEntry("refresh_date", 2))
            .thenReturn(null);
    Mockito.when(profileRepository.count()).thenReturn((long)testList.size());

    this.mockMvc.perform(get("/available/{page}", 3).header("X-GiTinder-token", "asd"))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\" : \"ok\","
                    + "\"message\" : \"No more available profiles for you!\""
                    + "}"));
  }

}
