package com.chromediopside.service;

import static org.junit.Assert.*;

import com.chromediopside.model.Match;
import com.chromediopside.model.Swiping;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MatchServiceTest {

  @Autowired
  private MatchService matchService;

  @Test
  public void getMatches() throws Exception {

  }

  @Test
  public void transformSwipingListToMatches() throws Exception {

  }

  @Test
  public void swipingToMatch() throws Exception {
    List<Swiping> swipeMatches = new ArrayList<>();
    Timestamp timestamp = new Timestamp(1498563273079l);
    swipeMatches.add(new Swiping("nori", "dani", timestamp));
    swipeMatches.add(new Swiping("nori", "dori", timestamp));
    swipeMatches.add(new Swiping("peti", "nori", timestamp));
    String actualUserName = "nori";

    List<Match> matchList = matchService.swipingToMatch(swipeMatches, actualUserName);

    List<Match> expected = new ArrayList<>();
    expected.add(new Match("dani", timestamp ));
    expected.add(new Match("dori", timestamp ));
    expected.add(new Match("peti", timestamp ));

    assertEquals(expected, matchList);
  }



}