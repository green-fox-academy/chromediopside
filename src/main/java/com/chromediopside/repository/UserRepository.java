package com.chromediopside.repository;

import com.chromediopside.model.GiTinderUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nóra on 2017. 06. 15..
 */
@Repository
public interface UserRepository extends CrudRepository<GiTinderUser, String> {

  GiTinderUser findOneByUsername(String username);

  GiTinderUser findOneByAccessToken(String accessToken);

  GiTinderUser findOneByAppToken(String appToken);

  GiTinderUser save(GiTinderUser giTinderUser);

}
