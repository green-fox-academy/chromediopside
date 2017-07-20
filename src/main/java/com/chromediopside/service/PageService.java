package com.chromediopside.service;

import com.chromediopside.datatransfer.ProfileResponse;
import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Page;
import com.chromediopside.repository.ProfileRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService {

  static final int PROFILES_PER_PAGE = 10;
  private Page page;
  private ProfileRepository profileRepository;

  @Autowired
  public PageService(ProfileRepository profileRepository, Page page) {
    this.profileRepository = profileRepository;
    this.page = page;
  }

  public Page setPage(int givenPageNumber) {
    List<GiTinderProfile> listOfProfiles = listProfilesPerPage(givenPageNumber);
    List<ProfileResponse> profileResponses = new ArrayList<>();
    for (GiTinderProfile giTinderProfile :
        listOfProfiles) {
      profileResponses.add(new ProfileResponse(giTinderProfile));
    }
    page.setProfiles(profileResponses);
    page.setCount(listOfProfiles.size());
    page.setAll(profileRepository.countNoSelf("p-czigany"));
    return page;
  }

  private List<GiTinderProfile> listProfilesPerPage(int givenPageNumber) {
    int offset = (givenPageNumber - 1) * PROFILES_PER_PAGE;
    List<GiTinderProfile> listOfProfilesPerPage = profileRepository
        .listTensOrderByEntry("p-czigany", randomSortingParam(), offset);
    return listOfProfilesPerPage;
  }

  private String randomSortingParam() {
    List<String> listOfSortingValues = Arrays
        .asList("login", "avatar_url", "repos", "refresh_date");
    Collections.shuffle(listOfSortingValues);
    return listOfSortingValues.get(0);
  }
}
