package com.chromediopside.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
  private static final String testRepos = "exam-basics;exam-trial-basics;"
      + "git-lesson-repository;lagopus-spring-exam;p-czigany.github.io";
  private static final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
  private Set<Language> testLanguagesList = new HashSet<>();

  private static final Timestamp lastRefresh = new Timestamp(1497441600000l);                  //2017-06-14 14:00:00.0
  private static final Timestamp eightHoursAfterLastRefresh = new Timestamp(1497470400000l);   //2017-06-14 22:00:00.0
  private static final Timestamp fourDaysAfterLastRefresh = new Timestamp(1497794400000l);     //2017-06-18 16:00:00.0

  private static final long oneDayInMillis = 86400000;

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
        .setRefreshDate(currentTime)
        .build();
    GiTinderProfile actualProfile = profileService
        .fetchProfileFromGitHub(validAccessToken, testLogin);
    assertEquals(expectedProfile, actualProfile);
  }

  @Test
  public void invalidAccessToken() throws Exception {
    GiTinderProfile actualProfile = profileService
        .fetchProfileFromGitHub(invalidAccessToken, testLogin);
    assertNull(actualProfile);
  }

  @Test
  public void daysPassedWhenEightHoursPassedBetweenDates() throws Exception {
    assertEquals(0, profileService.daysPassedBetweenDates(lastRefresh, eightHoursAfterLastRefresh));
  }

  @Test
  public void daysPassedWhenFourDaysPassedBetweenDates() throws Exception {
    assertEquals(4, profileService.daysPassedBetweenDates(lastRefresh, fourDaysAfterLastRefresh));
  }

  @Test
  public void daysPassedAfter24Hours() throws Exception {
    assertEquals(1, profileService.daysPassedBetweenDates(lastRefresh, new Timestamp(lastRefresh.getTime() - oneDayInMillis)));
  }

  @Test
  public void refreshRequiredIfZeroHoursPassed() throws Exception {
    GiTinderProfile profileToCheck = new GiTinderProfile();
    profileToCheck.setRefreshDate(new Timestamp(System.currentTimeMillis()));
    assertFalse(profileService.refreshRequired(profileToCheck));
  }

  @Test
  public void refreshRequiredSince1970() throws Exception {
    GiTinderProfile profileToCheck = new GiTinderProfile();
    profileToCheck.setRefreshDate(new Timestamp(00000000000l));
    assertTrue(profileService.refreshRequired(profileToCheck));
  }

  @Test
  public void refreshRequiredSinceNowMinus24Hours() throws Exception {
    GiTinderProfile profileToCheck = new GiTinderProfile();
    profileToCheck.setRefreshDate(new Timestamp(currentTime.getTime() - oneDayInMillis));
    assertTrue(profileService.refreshRequired(profileToCheck));
  }
}
