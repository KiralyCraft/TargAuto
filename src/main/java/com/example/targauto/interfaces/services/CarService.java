package com.example.targauto.interfaces.services;

import com.example.targauto.models.Car;
import com.example.targauto.models.User;

import java.util.List;
import java.util.Optional;

public interface CarService {
  List<Car> getSomeCars(int pageItems, int page);

  List<Car> getAllCarsInAuction();

  List<Car> getAllCarsInAuctionForAUser(User user);

  Optional<Car> auctionCar(Car car, User user);

  boolean canTakeNext(int pageItems, int page);

  Optional<Car> getCarById(String carId);
}
