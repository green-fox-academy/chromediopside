package com.chromediopside.repository;

import com.chromediopside.model.GiTinderProfile;
import com.chromediopside.model.Language;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<GiTinderProfile, String> {

  GiTinderProfile findByLogin(String login);

}
