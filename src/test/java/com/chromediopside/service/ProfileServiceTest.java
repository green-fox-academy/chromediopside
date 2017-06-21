package com.chromediopside.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.chromediopside.mockbuilder.MockProfileBuilder;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProfileServiceTest {

  private static final String validAccessToken = System.getenv("TEST_ACCESS_TOKEN");
  private static final String invalidAccessToken = "1nval1dt0k3n";
  private static final String testLogin = System.getenv("TEST_LOGIN");
  private static final String testAvatarUrl = System.getenv("TEST_AVATAR_URL");
  private static final String testRepos = "chromediopsite;exam-basics;exam-trial-basics;"
      + "git-lesson-repository;lagopus-spring-exam;p-czigany.github.io";
  private static final Timestamp testTimeStamp = new Timestamp(System.currentTimeMillis());
  private Set<Language> testLanguagesList = new HashSet<>();

  @Autowired
  private ProfileService profileService;
  @Autowired
  private MockProfileBuilder mockProfileBuilder;

  @Before
  public void setup() throws Exception {
    testLanguagesList.add(new Language("Java"));
    testLanguagesList.add(new Language("HTML"));
  }

  @Test
  public void validAccessToken() throws Exception {
    GiTinderProfile expectedProfile = mockProfileBuilder
        .setLogin(testLogin)
        .setAvatarUrl(testAvatarUrl)
        .setRepos(testRepos)
        .setLanguagesList(testLanguagesList)
        .setRefreshDate(testTimeStamp)
        .build();
    GiTinderProfile actualProfile = profileService.getProfileFromGitHub(validAccessToken);
    assertEquals(expectedProfile, actualProfile);
  }

  @Test
  public void invalidAccessToken() throws Exception {
    GiTinderProfile actualProfile = profileService.getProfileFromGitHub(invalidAccessToken);
    assertNull(actualProfile);
  }
}
