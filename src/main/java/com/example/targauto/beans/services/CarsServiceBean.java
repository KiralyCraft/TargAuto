package com.example.targauto.beans.services;

import com.example.targauto.interfaces.repository.CarRepository;
import com.example.targauto.interfaces.services.CarService;
import com.example.targauto.models.Car;
import com.example.targauto.models.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Optional;

@Stateless
@Local(CarService.class)
public class CarsServiceBean implements CarService {
  @EJB() CarRepository carRepository;

  @Override
  public Optional<Car> getCarById(String carId) {
    return carRepository.getCarById(carId);
  }

  @Override
  public List<Car> getAllCarsInAuction() {
    return carRepository.getAllCarsWithStatusInAuction();
  }

  @Override
  public List<Car> getAllCarsInAuctionOwnedByUser(User user) {
    return carRepository.getAllCarsForUserAndWithStatusInAuction(user);
  }

  @Override
  public Optional<Car> auctionCar(Car car, User user) {
    return carRepository.createCar(car, user);
  }
}
