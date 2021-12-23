package com.example.targauto.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
public class User implements Serializable {
  @Id private String id;
  private String email;
  private String username;
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Offer> offers;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Car> cars;

  public User() {}

  public User(String username, String email, String password) {
    this.id = UUID.randomUUID().toString();
    this.username = username.trim();
    this.email = email.trim();
    this.password = password.trim();
  }

  public List<Car> getCars() {
    return cars;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String validateNewUser(String confirmPassword) {
    if (username.length() < 6) return "username_length";
    if (password.length() < 6) return "password_length";
    if (confirmPassword.length() < 6) return "conform_length";
    if (!password.equals(confirmPassword)) return "confirm";
    if (!email.contains("@")) return "email";
    return null;
  }

  public String getId() {
    return id;
  }

  public void setId(String userId) {
    this.id = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> commands) {
    this.offers = commands;
  }

  public void addOffer(Offer offer) {
    this.offers.add(offer);
    offer.setUser(this);
  }

  public void removeOffer(Offer offer) {
    this.offers.remove(offer);
    offer.setUser(null);
  }

  public void addCar(Car car) {
    this.cars.add(car);
    car.setUser(this);
  }

  public void removeCar(Car car) {
    this.cars.remove(car);
    car.setUser(null);
  }
}
