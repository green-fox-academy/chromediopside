package com.chromediopside.model;

import java.util.List;
import com.chromediopside.datatransfer.ProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class Page {

  private List<ProfileResponse> profiles;
  private int count;
  private int all;

  public Page() {
  }

  public Page(List<ProfileResponse> profiles, int count, int all) {
    this.profiles = profiles;
    this.count = count;
    this.all = all;
  }

  @Override
  public String toString() {
    String profilesToString = "";
    for (ProfileResponse profileElement : profiles) {
      profilesToString =
              profileElement.getLogin() + ", " + profileElement.getAvatarUrl() + ", " + profileElement.getRepos()
                      + ", " + profileElement.getLanguages();
    }
    return profilesToString + ", " + count + ", " + all;
  }

  public List<ProfileResponse> getProfiles() {
    return profiles;
  }

  public void setProfiles(List<ProfileResponse> profiles) {
    this.profiles = profiles;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getAll() {
    return all;
  }

  public void setAll(int all) {
    this.all = all;
  }
}
