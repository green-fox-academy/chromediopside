package com.chromediopside.repository;

import com.chromediopside.model.GiTinderProfile;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<GiTinderProfile, String> {

  GiTinderProfile findByLogin(String login);
  boolean existsByLogin(String login);

  @Query(value = "SELECT login, avatar_url, repos, language_id, refresh_date, random_code_links "
          + "FROM gitinder_profile "
          + "JOIN language_to_profile "
          + "ON gitinder_profile.login = language_to_profile.profile_id "
          + "WHERE language_name LIKE ?1 ORDER BY ?2 ASC LIMIT 10 OFFSET ?3", nativeQuery = true)
  List<GiTinderProfile> listTensByLanguageOrderByEntry(
          String languageName, String sortingParam, int offset);

  @Query(value = "SELECT login, avatar_url, repos, refresh_date, random_code_links "
          + "FROM gitinder_profile "
          + "ORDER BY ?1 ASC LIMIT 10 OFFSET ?2", nativeQuery = true)
  List<GiTinderProfile> listTensOrderByEntry(String sortingParam, int offset);
}
