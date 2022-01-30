package com.example.targauto.managed;

import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.interfaces.services.OfferService;
import com.example.targauto.models.Car;
import com.example.targauto.models.User;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@Named("selectedCarManagedBean")
@ManagedBean
@ViewScoped
public class SelectedCarManagedBean implements Serializable {
  @EJB CarService carService;

  @EJB OfferService offerService;

  private String carId;
  private Car car;
  private double price;

  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  public Car getCar() {
    var carFromDatabase = carService.getCarById(carId);
    if (carFromDatabase.isEmpty()) car = new Car("Not Found", "Not found", 0);
    else car = carFromDatabase.get();
    return car;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean showMakeOffer(Optional<User> user) {
    return (user.isPresent() && !(Objects.equals(user.get().getId(), car.getUser().getId())));
  }

  public void makeOffer(Optional<User> user) {
    if (user.isEmpty() || Objects.equals(user.get().getId(), car.getUser().getId()) || price <= 0) {
      return;
    }
    offerService.processUserOffer(user.get(), car, price);
  }
}
