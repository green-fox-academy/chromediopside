package com.chromediopside.repository;

import com.chromediopside.model.GiTinderProfile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<GiTinderProfile, String> {

  GiTinderProfile findByLogin(String login);
}
