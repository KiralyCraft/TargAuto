package com.example.targauto.beans.repository;

import com.example.targauto.interfaces.repository.OfferRepository;
import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(OfferRepository.class)
public class OfferRepositoryBean implements OfferRepository {
  @PersistenceContext(unitName = "targAuto")
  private EntityManager manager;

  @Override
  public Optional<Offer> createOffer(User user, Car car, double price) {
    try {
      Offer command = new Offer(user, car, price);
      var userFromDatabase = manager.find(User.class, user.getId());
      userFromDatabase.addOffer(command);
      manager.persist(command);

      return Optional.of(command);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Offer> getOfferForCarByUser(User user, Car car) {
    try {
      var userFromDatabase = manager.find(User.class, user.getId());
      var userOffers = userFromDatabase.getOffers();
      var offerMadeByUser =
          userOffers.stream()
              .filter(
                  offer ->
                      car.getOffers().stream()
                          .anyMatch(offer1 -> offer.getId().equals(offer1.getId())))
              .findFirst();
      return offerMadeByUser;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("Failed to find any offer made by this user for this car");
      return Optional.empty();
    }
  }

  @Override
  public List<Offer> getOffersForCar(Car car) {
    try {
      var carFromDatabase = manager.find(Car.class, car.getId());
      carFromDatabase.getOffers().forEach(offer -> offer.getUser());
      return carFromDatabase.getOffers();
    } catch (Exception e) {
      System.out.println("Failed to get all the offers for a car");
      return List.of();
    }
  }

  @Override
  public Optional<Offer> updateOfferPrice(Offer oldOffer, double price) {
    try {
      var offerFromDatabase = manager.find(Offer.class, oldOffer.getId());
      offerFromDatabase.setPrice(price);
      offerFromDatabase.setOfferDate(LocalDate.now());
      return Optional.of(offerFromDatabase);
    } catch (Exception e) {
      System.out.println("Failed to update the offer price");
      return Optional.empty();
    }
  }

  @Override
  public void updateOfferStatus(Offer oldOffer, String status) {
    try {
      var offerFromDatabase = manager.find(Offer.class, oldOffer.getId());
      offerFromDatabase.setStatus(status);
    } catch (Exception e) {
      System.out.println("Failed to update the offer status");
    }
  }

  @Override
  public List<Offer> getAllOffersForUser(User user) {
    try {
      var userFromDatabase = manager.find(User.class, user.getId());
      var offersForUser =
          userFromDatabase.getCars().stream()
              .map(Car::getOffers)
              .flatMap(List::stream)
              .collect(Collectors.toList());
      offersForUser.forEach(
          offer -> {
            offer.getUser();
            offer.getCar();
          });
      return offersForUser;
    } catch (Exception e) {
      System.out.println("Failed to get all offers for user");
      return List.of();
    }
  }

  @Override
  public List<Offer> getAllOffersByUser(User user) {
    try {
      var userFromDatabase = manager.find(User.class, user.getId());
      var offersMadeByUser = userFromDatabase.getOffers();
      offersMadeByUser.forEach(
          offer -> {
            offer.getUser();
            offer.getCar().getUser();
          });
      return offersMadeByUser;
    } catch (Exception e) {
      System.out.println("Failed to get all offers made by user");
      return List.of();
    }
  }
}
