package com.example.targauto.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Offers")
public class Offer implements Serializable {
  @Id private String id;
  @ManyToOne private Car car;
  @ManyToOne private User user;
  private LocalDate offerDate;
  private double price;
  private String status;

  public Offer(User user, Car car, double price) {
    this.price = price;
    this.id = UUID.randomUUID().toString();
    this.offerDate = LocalDate.now();
    this.car = car;
    this.user = user;
    this.status = "pending";
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

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
