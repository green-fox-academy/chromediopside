package com.chromediopside.datatransfer;

import com.chromediopside.model.Match;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class Matches {

  private List<Match> matches;

  public Matches() {
  }

  public List<Match> getMatches() {
    return matches;
  }

  public void setMatches(List<Match> matches) {
    this.matches = matches;
  }
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Matches)) {
      return false;
    }
    Matches matches = (Matches) o;
    return Objects.equals(matches, matches.matches);
  }
}
