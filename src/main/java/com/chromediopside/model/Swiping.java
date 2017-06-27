package com.chromediopside.model;

import com.chromediopside.model.Swiping.SwipeId;
import java.io.Serializable;
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


  public class SwipeId implements Serializable {

    private String swipingUsersName;
    private String swipedUsersName;

  }
}
