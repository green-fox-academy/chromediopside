package com.chromediopside.model;

import javafx.geometry.HorizontalDirection;
import javax.persistence.Column;
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
  @Column (name = "horizontal_direction")
  private HorizontalDirection swipeDirection;

  public Swiping() {
  }

  public Swiping(String swipingUsersName, String swipedUsersName,
          HorizontalDirection swipeDirection) {
    this.swipingUsersName = swipingUsersName;
    this.swipedUsersName = swipedUsersName;
    this.swipeDirection = swipeDirection;
  }
}
