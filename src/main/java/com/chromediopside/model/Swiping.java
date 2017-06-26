package com.chromediopside.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(SwipeId.class)
public class Swiping {

  @Id
  private String swipingUsersName;
  @Id
  private String swipedUsersName;

  public Swiping() {
  }

  public Swiping(String swipingUsersName, String swipedUsersName) {
    this.swipingUsersName = swipingUsersName;
    this.swipedUsersName = swipedUsersName;
  }
}
