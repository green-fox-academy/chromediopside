package com.chromediopside.model;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Page {

  List<GiTinderProfile> profiles;
  int count;
  int all;

  public Page(List<GiTinderProfile> profiles, int count, int all) {
    this.profiles = profiles;
    this.count = count;
    this.all = all;
  }

  public Page() {
  }

  public List<GiTinderProfile> getProfiles() {
    return profiles;
  }

  public void setProfiles(List<GiTinderProfile> profiles) {
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
