package com.example.targauto.managed;

import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.models.Car;
import com.example.targauto.utils.SessionUtils;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("carsManagedBean")
@ManagedBean
@SessionScoped
public class CarsManagedBean implements Serializable {
  @EJB CarService carService;

  public List<Car> getListOfAllCarsInAuction() {
    return carService.getAllCarsInAuction();
  }

  public List<Car> getListOfAllCarsInAuctionOfAUser() {
    var user = SessionUtils.getUser();
    if (user.isEmpty()) return List.of();
    return carService.getAllCarsInAuctionForAUser(user.get());
  }
}
