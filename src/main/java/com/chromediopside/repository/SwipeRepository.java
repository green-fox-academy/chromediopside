package com.chromediopside.repository;

import com.chromediopside.model.SwipeDirection;
import com.chromediopside.model.Swiping;
import com.chromediopside.model.SwipeId;
import org.springframework.data.repository.CrudRepository;

public interface SwipeRepository extends CrudRepository<Swiping, SwipeId> {

  Swiping findBySwipingUsersNameAndSwipedUsersNameAndSwipeDirection(String swipingUsersName,
          String swipedUsersName, SwipeDirection horizontalDirection);
}
