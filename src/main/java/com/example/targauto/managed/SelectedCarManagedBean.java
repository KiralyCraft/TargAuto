package com.example.targauto.managed;

import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.models.Car;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("selectedCarManagedBean")
@ManagedBean
@ViewScoped
public class SelectedCarManagedBean implements Serializable {
  @EJB CarService carService;
  private String carId;
  private Car car;

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
}
