package com.chromediopside.service;

import static org.junit.Assert.*;

import com.chromediopside.datatransfer.Matches;
import com.chromediopside.mockbuilder.MockUserBuilder;
import com.chromediopside.model.Match;
import com.chromediopside.model.Swiping;
import com.chromediopside.repository.SwipeRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MatchServiceTest {

  @Autowired
  private MatchService matchService;
  @MockBean
  private GiTinderUserService userService;
  @Autowired
  private MockUserBuilder mockUserBuilder;
  @MockBean
  private SwipeRepository swipeRepository;

  @Test
  public void getMatches() throws Exception {
    String appToken = "321";
    String actualUsersName = "nori";
    Mockito.when(userService.getUserByAppToken(appToken)).thenReturn(
            mockUserBuilder.setAccessToken("123")
            .setAppToken("321")
            .setUserName(actualUsersName)
            .build());

    List<Swiping> swipeMatches = new ArrayList<>();
    Timestamp timestamp = new Timestamp(1498563273079l);
    String avatarUrl = "https://avatars1.githubusercontent.com/u/26329189?v=3";
    swipeMatches.add(new Swiping("nori", "dani", timestamp));
    swipeMatches.add(new Swiping("nori", "dori", timestamp));
    swipeMatches.add(new Swiping("peti", "nori", timestamp));
    Mockito.when(swipeRepository.findSwipeMatches(actualUsersName)).thenReturn(swipeMatches);

    Matches matches = matchService.getMatches(appToken);

    List<Match> matchList = new ArrayList<>();
    matchList.add(new Match("dani", avatarUrl, timestamp ));
    matchList.add(new Match("dori", avatarUrl, timestamp ));
    matchList.add(new Match("peti", avatarUrl, timestamp ));
    Matches expected = new Matches();
    expected.setMatches(matchList);
    assertEquals(expected.getMatches(), matches.getMatches());
  }

  @Test
  public void transformSwipingListToMatches() throws Exception {
    List<Swiping> swipeMatches = new ArrayList<>();
    Timestamp timestamp = new Timestamp(1498563273079l);
    String avatarUrl = "https://avatars1.githubusercontent.com/u/26329189?v=3";
    swipeMatches.add(new Swiping("nori", "dani", timestamp));
    swipeMatches.add(new Swiping("nori", "dori", timestamp));
    swipeMatches.add(new Swiping("peti", "nori", timestamp));
    String actualUserName = "nori";

    Matches matches = matchService.transformSwipingListToMatches(swipeMatches, actualUserName);

    List<Match> matchList = new ArrayList<>();
    matchList.add(new Match("dani", avatarUrl, timestamp ));
    matchList.add(new Match("dori", avatarUrl, timestamp ));
    matchList.add(new Match("peti", avatarUrl, timestamp ));
    Matches expected = new Matches();
    expected.setMatches(matchList);

    assertEquals(expected.getMatches(), matches.getMatches());
  }

  @Test
  public void swipingToMatch() throws Exception {
    List<Swiping> swipeMatches = new ArrayList<>();
    Timestamp timestamp = new Timestamp(1498563273079l);
    String avatarUrl = "https://avatars1.githubusercontent.com/u/26329189?v=3";

    swipeMatches.add(new Swiping("nori", "dani", timestamp));
    swipeMatches.add(new Swiping("nori", "dori", timestamp));
    swipeMatches.add(new Swiping("peti", "nori", timestamp));
    String actualUserName = "nori";

    List<Match> matchList = matchService.swipingToMatch(swipeMatches, actualUserName);

    List<Match> expected = new ArrayList<>();
    expected.add(new Match("dani", avatarUrl, timestamp ));
    expected.add(new Match("dori", avatarUrl, timestamp ));
    expected.add(new Match("peti", avatarUrl, timestamp ));

    assertEquals(expected, matchList);
  }



}