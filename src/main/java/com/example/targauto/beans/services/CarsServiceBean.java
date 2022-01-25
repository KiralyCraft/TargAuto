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
import java.util.stream.Collectors;

@Stateless
@Local(CarService.class)
public class CarsServiceBean implements CarService {
  @EJB() CarRepository carRepository;

  @Override
  public List<Car> getSomeCars(int pageItems, int page) {
    return carRepository.getAllCars().stream()
        .skip((long) page * pageItems)
        .limit(pageItems)
        .collect(Collectors.toList());
  }

  @Override
  public boolean canTakeNext(int pageItems, int page) {
    return ((carRepository.getNrOfCars() - ((long) (page + 1) * pageItems)) > 0);
  }

  @Override
  public Optional<Car> getCarById(String carId) {
    return carRepository.getCarById(carId);
  }

  @Override
  public List<Car> getAllCarsInAuction() {
    return carRepository.getAllCarsWithStatusInAuction();
  }

  @Override
  public List<Car> getAllCarsInAuctionForAUser(User user) {
    return carRepository.getAllCarsForUserAndWithStatusInAuction(user);
  }

  @Override
  public Optional<Car> auctionCar(Car car, User user) {
    return carRepository.createCar(car, user);
  }
}
