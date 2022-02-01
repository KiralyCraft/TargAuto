package com.example.targauto.interfaces.services;

import com.example.targauto.models.Car;
import com.example.targauto.models.User;

import java.util.List;
import java.util.Optional;

public interface CarService {

  List<Car> getAllCarsInAuction();

  List<Car> getAllCarsInAuctionOwnedByUser(User user);

  Optional<Car> auctionCar(Car car, User user);

  Optional<Car> getCarById(String carId);

  boolean delistCarID(String carID);
}
