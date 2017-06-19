package com.chromediopside.repository;

import com.chromediopside.model.GiTinderUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nóra on 2017. 06. 15..
 */
@Repository
public interface UserRepository extends CrudRepository<GiTinderUser, String> {

  GiTinderUser findByUsername(String username);

  GiTinderUser findByAccessToken(String accessToken);

  GiTinderUser findByAppToken(String appToken);
  
}
