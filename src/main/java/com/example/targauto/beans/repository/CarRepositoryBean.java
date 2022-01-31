package com.example.targauto.beans.repository;

import com.example.targauto.interfaces.repository.CarRepository;
import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@Stateless
@Local(CarRepository.class)
public class CarRepositoryBean implements CarRepository {
  @PersistenceContext(unitName = "targAuto")
  private EntityManager manager;

  @Override
  public List<Car> getAllCarsWithStatusInAuction() {
    try {
      TypedQuery<Car> query =
          manager.createQuery("select p from  Car p where p.status like 'inAuction'", Car.class);
      return query.getResultList();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return List.of();
    }
  }

  @Override
  public List<Car> getAllCarsForUserAndWithStatusInAuction(User user) {
    try {
      var userFromDatabase = manager.find(User.class, user.getId());
      userFromDatabase.getCars().forEach(Car::getId);
      return userFromDatabase.getCars();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return List.of();
    }
  }

  @Override
  public Optional<Car> createCar(Car car, User user) {
    try {
      var userFromDatabase = manager.find(User.class, user.getId());
      userFromDatabase.getCars();
      userFromDatabase.addCar(car);
      manager.persist(car);
      return Optional.of(car);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return Optional.empty();
    }
  }

  @Override
  public Optional<Car> getCarById(String carId) {
    try {
      Car car = manager.find(Car.class, carId);
      car.getUser();
      car.getOffers().forEach(Offer::getId);
      return Optional.of(car);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return Optional.empty();
    }
  }

  @Override
  public Optional<Car> updateCarStatus(Car car, String newStatus) {
    try {
      var carFromDatabase = manager.find(Car.class, car.getId());
      carFromDatabase.setStatus(newStatus);
      return Optional.of(carFromDatabase);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("Failed to update car status");
      return Optional.empty();
    }
  }
}
