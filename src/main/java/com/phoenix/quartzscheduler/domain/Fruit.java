package com.phoenix.quartzscheduler.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fruit extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long fruitId;

  private String fruitName;


  public long getFruitId() {
    return fruitId;
  }

  public void setFruitId(long fruitId) {
    this.fruitId = fruitId;
  }

  public String getFruitName() {
    return fruitName;
  }

  public void setFruitName(String fruitName) {
    this.fruitName = fruitName;
  }


}
