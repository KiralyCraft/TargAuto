package com.example.targauto.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Offers")
public class Offer implements Serializable {
  @Id private String id;
  @ManyToOne
  private Car car ;
  @ManyToOne private User user;
  private LocalDate offerDate;

  public Offer(Car car,String address) {
    this.id = UUID.randomUUID().toString();
    this.offerDate = LocalDate.now();
    this.car = car;
  }

  public Offer() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getId() {
    return id;
  }

  public void setId(String commandId) {
    this.id = commandId;
  }

  public String getUserId() {
    return user.getId();
  }

  public LocalDate getOfferDate() {
    return offerDate;
  }

  public void setOfferDate(LocalDate commandDate) {
    this.offerDate = commandDate;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

}
