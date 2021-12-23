package com.example.targauto.interfaces.repository;

import com.example.targauto.models.Car;
import com.example.targauto.models.User;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
  long getNrOfCars();

  List<Car> getAllCars();

  List<Car> getAllCarsInAuction();

  List<Car> getAllCarsByUserAndInAuction(User user);

  Optional<Car> getCarById(String carId);

  void changeCarStatus(String carId, String newStatus);

  Optional<Car> createCar(Car car, User user);
}
