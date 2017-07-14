package com.chromediopside.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.chromediopside.GitinderApplication;
import com.chromediopside.mockbuilder.MockUserBuilder;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Swiping;
import com.chromediopside.repository.ProfileRepository;
import com.chromediopside.repository.SwipeRepository;
import com.chromediopside.repository.UserRepository;
import com.chromediopside.service.ProfileService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GitinderApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableWebMvc
public class MatchControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private UserRepository userRepository;
  @MockBean
  private SwipeRepository swipeRepository;
  @MockBean
  private ProfileRepository profileRepository;

  @Autowired
  private MockUserBuilder mockUserBuilder;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void getMatches() throws Exception {

    Mockito.when(userRepository.findByAppToken("321")).thenReturn(
            mockUserBuilder.setAccessToken("123")
                    .setAppToken("321")
                    .setUserName("nori")
                    .build());

    Timestamp timestamp = new Timestamp(1498563273079l);
    Swiping s1 = new Swiping("nori", "dani", timestamp);
    Swiping s2 = new Swiping("nori", "dori", timestamp);
    Swiping s3 = new Swiping("peti", "nori", timestamp);
    List<Swiping> swipingList = new ArrayList<>();
    swipingList.add(s1);
    swipingList.add(s2);
    swipingList.add(s3);
    Mockito.when(swipeRepository.findSwipeMatches("nori")).thenReturn(swipingList);

    String avatarUrl = "https://avatars1.githubusercontent.com/u/26329189?v=3";
    GiTinderProfile daniprofile = new GiTinderProfile("dani", avatarUrl);
    GiTinderProfile doriprofile = new GiTinderProfile("dori", avatarUrl);
    GiTinderProfile petiprofile = new GiTinderProfile("peti", avatarUrl);
    Mockito.when(profileRepository.findByLogin("dani")).thenReturn(daniprofile);
    Mockito.when(profileRepository.findByLogin("dori")).thenReturn(doriprofile);
    Mockito.when(profileRepository.findByLogin("peti")).thenReturn(petiprofile);

    mockMvc.perform(get("/matches").header("X-GiTinder-token", "321"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andDo(print())
          .andExpect(content().json( "{"
                  + "\"matches\": ["
                  + "{"
                  + "\"username\": \"dani\","
                  + "\"avatar_url\": \"https://avatars1.githubusercontent.com/u/26329189?v=3\","
                  + "\"messages\": null,"
                  + "\"matched_at\": 1498563273079},"
                  + "{"
                  + "\"username\": \"dori\","
                  + "\"avatar_url\":\"https://avatars1.githubusercontent.com/u/26329189?v=3\","
                  + "\"messages\": null,"
                  + "\"matched_at\": 1498563273079},"
                  + "{"
                  + "\"username\": \"peti\","
                  + "\"avatar_url\":\"https://avatars1.githubusercontent.com/u/26329189?v=3\","
                  + "\"messages\": null,"
                  + "\"matched_at\": 1498563273079}]}"));
  }

  @Test
  public void getMatchesWithoutAppToken() throws Exception {
    mockMvc.perform(get("/matches"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    +"\"status\": \"error\","
                    +"\"message\": \"Unauthorized request!\"}"));
  }

  @Test
  public void getMatchesWithInvalidAppToken() throws Exception {
    mockMvc.perform(get("/matches").header("X-GiTinder-token", "abc"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json("{"
                    + "\"status\": \"error\","
                    + "\"message\": \"Unauthorized request!\"}"));
  }
}