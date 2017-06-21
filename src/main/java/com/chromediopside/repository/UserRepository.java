package com.chromediopside.repository;

import com.chromediopside.model.GiTinderUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<GiTinderUser, String> {

  GiTinderUser findByUserName(String userName);

  GiTinderUser findByAccessToken(String accessToken);

  GiTinderUser findByAppToken(String appToken);

}
