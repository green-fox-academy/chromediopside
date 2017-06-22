package com.chromediopside.repository;

import com.chromediopside.model.GiTinderProfile;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<GiTinderProfile, String> {

  GiTinderProfile findByLogin(String login);

  @Query(value = "SELECT login, avatar_url, repos, language_id "
          + "FROM gitinder_profile "
          + "JOIN language_to_profile "
          + "ON gitinder_profile.login = language_to_profile.profile_id "
          + "WHERE language_name LIKE ?1 ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
  List<GiTinderProfile> selectTenRandomLanguageName(@Param("language_name") String languageName);
}
