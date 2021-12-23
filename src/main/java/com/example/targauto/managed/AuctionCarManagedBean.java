package com.example.targauto.managed;

import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.models.Car;
import com.example.targauto.utils.SessionUtils;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("auctionCarManagedBean")
@ManagedBean
@SessionScoped
public class AuctionCarManagedBean implements Serializable {
  @EJB CarService carService;

  private String name;
  private String description;
  private double price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String giveCarForAuction() {
    var car = new Car(name, description, price);
    var user = SessionUtils.getUser();
    if (user.isEmpty()) return "AuctionCar";
    var auctionCar = carService.auctionCar(car, user.get());
    if (auctionCar.isEmpty()) return "AuctionCar";
    return "Home";
  }
}
