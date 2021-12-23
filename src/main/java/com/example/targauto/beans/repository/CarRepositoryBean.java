package com.example.targauto.beans.repository;

import com.example.targauto.interfaces.repository.CarRepository;
import com.example.targauto.models.Car;
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
  public long getNrOfCars() {
    try {
      TypedQuery<Car> query = manager.createQuery("select p from  Car p ", Car.class);
      return query.getResultStream().count();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return -1;
    }
  }

  @Override
  public List<Car> getAllCars() {
    try {
      TypedQuery<Car> query = manager.createQuery("select p from  Car p ", Car.class);
      return query.getResultList();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return List.of();
    }
  }

  @Override
  public List<Car> getAllCarsInAuction() {
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
  public List<Car> getAllCarsByUserAndInAuction(User user) {
    try {
      var userFromDatabase = manager.find(User.class, user.getId());
      // without this forEach I get failed to lazily initialize a collection of role:
      // com.example.targauto.models.User.cars, could not initialize proxy - no Session,
      // dont ask me why
      userFromDatabase
          .getCars()
          .forEach(
              car -> {
                car.getName();
                car.getDescription();
                car.getPrice();
              });
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
      // cars from User are lazily initialized, I need to do this to get them from the database, if
      // i dont do this I get failed to lazily initialize a collection of role:
      // com.example.targauto.models.User.cars, could not initialize proxy - no Session
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
      return Optional.of(manager.find(Car.class, carId));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return Optional.empty();
    }
  }

  @Override
  public void changeCarStatus(String carId, String newStatus) {
    try {
      var productToBeRemoved = getCarById(carId);
      if (productToBeRemoved.isEmpty()) return;
      productToBeRemoved.get().setStatus(newStatus);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
