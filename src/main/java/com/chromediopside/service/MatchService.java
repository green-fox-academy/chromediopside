package com.chromediopside.service;

import com.chromediopside.datatransfer.Matches;
import com.chromediopside.model.GiTinderUser;
import com.chromediopside.model.Match;
import com.chromediopside.model.Swiping;
import com.chromediopside.repository.SwipeRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

  private GiTinderUserService userService;
  private SwipeRepository swipeRepository;

  @Autowired
  public MatchService(GiTinderUserService userService,
          SwipeRepository swipeRepository) {
    this.userService = userService;
    this.swipeRepository = swipeRepository;
  }

  public Matches getMatches(String appToken) {
    GiTinderUser actualUser = userService.getUserByAppToken(appToken);
    String actualUsersName = actualUser.getUserName();
    List<Swiping> swipeMatches = swipeRepository.findSwipeMatches(actualUsersName);
    return transformSwipeMatchesListToMatches(swipeMatches, actualUsersName);
  }

  private Matches transformSwipeMatchesListToMatches(List<Swiping> swipeMatches, String actualUsersName) {
    Matches matches = new Matches();
    List<Match> matchList = transformSwipeListToMatchList(swipeMatches, actualUsersName);
    matches.setMatches(matchList);
    return matches;
  }

  private List<Match> transformSwipeListToMatchList(List<Swiping> swipeMatches, String actualUsersName) {
    List<Match> matchList = new ArrayList<>();
    for (Swiping swipe : swipeMatches) {
      Match match = new Match();

      String username = swipe.getSwipingUsersName();
      if (swipe.getSwipingUsersName().equals(actualUsersName)) {
        username = swipe.getSwipedUsersName();
      }
      match.setUsername(username);

      Timestamp matched_at = swipe.getTimestamp();
      match.setMatched_at(matched_at);

      matchList.add(match);
    }
    return matchList;
  }
}
