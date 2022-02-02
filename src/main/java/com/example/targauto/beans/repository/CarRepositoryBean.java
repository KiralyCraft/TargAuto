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

  /**
   * Returns a List (potentially empty) of all the currently-auctioned cars.
   */
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

  /**
   * Returns a potentially empty List of all the Cars in an auction, belonging to the given User.
   * No further checks are made for the validity of the given User.
   */
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

  /**
   * Creates a Car entry that is mapped to the given User, and persists it in the database.
   * If it succeeds, it returns an Optional Car - It might be missing if an error occured.
   */
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

  /**
   * Returns an Optional Car, searched for using the provided ID. If it does not match exactly to 
   * a Car's from the database, it returns an empty Optional.
   */
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
  
  /**
   * Updates a Car's status with the provided status. Arbitrary statuses are permitted.
   * It returns the updated car, or an empty Optional if the update failed 
   */
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
/**
 * Attempts to delist the Car with the provided ID. If such a Car does not exist, the return result is false.
 */
	@Override
	public boolean delistCar(String carID)
	{
		try
		{
			Car theCar = manager.find(Car.class, carID);
			if (theCar != null)
			{
				manager.remove(theCar);
				return true;
			}
		}
		catch(Exception e)
		{
			System.err.println("Failed to delist car:");
			e.printStackTrace();
		}
		return false;
	}
}
