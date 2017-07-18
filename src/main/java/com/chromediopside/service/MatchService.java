package com.chromediopside.service;

import com.chromediopside.datatransfer.Matches;
import com.chromediopside.datatransfer.MessageDTO;
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
  private ProfileService profileService;

  @Autowired
  public MatchService(GiTinderUserService userService,
          SwipeRepository swipeRepository,
          ProfileService profileService) {
    this.userService = userService;
    this.swipeRepository = swipeRepository;
    this.profileService = profileService;
  }

  public Matches getMatches(String appToken) {
    GiTinderUser actualUser = userService.getUserByAppToken(appToken);
    String actualUsersName = actualUser.getUserName();
    List<Swiping> swipeMatches = swipeRepository.findSwipeMatches(actualUsersName);
    Matches matches = transformSwipingListToMatches(swipeMatches, actualUsersName);
    return matches;
  }

  public Matches transformSwipingListToMatches(List<Swiping> swipeMatches, String actualUsersName) {
    Matches matches = new Matches();
    List<Match> matchList = swipingToMatch(swipeMatches, actualUsersName);
    matches.setMatches(matchList);
    return matches;
  }

  public List<Match> swipingToMatch(List<Swiping> swipeMatches, String actualUsersName) {
    List<Match> matchList = new ArrayList<>();
    for (Swiping swipe : swipeMatches) {

      String username = swipe.getSwipingUsersName();
      if (swipe.getSwipingUsersName().equals(actualUsersName)) {
        username = swipe.getSwipedUsersName();
      }
      String avatarUrl = profileService.getAvatarUrlByUsername(username);
      Timestamp matched_at = swipe.getTimestamp();

      Match match = new Match(username, avatarUrl, matched_at);
      matchList.add(match);
    }
    return matchList;
  }

  public boolean areTheyMatched(String appToken, MessageDTO messageDTO) {
    GiTinderUser actualUser = userService.getUserByAppToken(appToken);
    String actualUsersName = actualUser.getUserName();
    String otherUsersName = messageDTO.getTo();
    boolean areTheyMatched = swipeRepository.areTheyMatched(actualUsersName, otherUsersName);
    return areTheyMatched;
  }
}
