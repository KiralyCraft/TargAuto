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
import java.util.Optional;

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
  public Optional<Offer> getUserOfferForCar(User user, Car car) {
    try {
      var userFromDatabase = manager.find(User.class, user.getId());
      var userOffers = userFromDatabase.getOffers();
      var offerMadeByUser =
          userOffers.stream()
              .filter(
                  offer ->
                      car.getOffer().stream()
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
  public Optional<Offer> updateOffer(Offer oldOffer, double price) {
    try {
      var offerFromDatabase = manager.find(Offer.class, oldOffer.getId());
      offerFromDatabase.setPrice(price);
      offerFromDatabase.setOfferDate(LocalDate.now());
      return Optional.of(offerFromDatabase);
    } catch (Exception e) {
      System.out.println("Failed to update the offer");
      return Optional.empty();
    }
  }
}
