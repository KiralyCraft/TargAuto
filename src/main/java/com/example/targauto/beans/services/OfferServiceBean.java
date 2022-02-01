package com.example.targauto.beans.services;

import com.example.targauto.interfaces.repository.CarRepository;
import com.example.targauto.interfaces.repository.OfferRepository;
import com.example.targauto.interfaces.services.OfferService;
import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Local(OfferService.class)
public class OfferServiceBean implements OfferService {
  @EJB OfferRepository offerRepository;
  @EJB CarRepository carRepository;

  @Override
  public Optional<Offer> processUserOffer(User user, Car car, double price) {
    var offerMadeByUser = offerRepository.getOfferForCarByUser(user, car);
    if (offerMadeByUser.isPresent())
      return offerRepository.updateOfferPrice(offerMadeByUser.get(), price);
    return offerRepository.createOffer(user, car, price);
  }

  @Override
  public void acceptCarOffer(Offer acceptedOffer) {
    try {
      offerRepository.updateOfferStatus(acceptedOffer, "accepted");
      Optional<Car> theCar = carRepository.getCarById(acceptedOffer.getCar().getId());
      var rejectedOffers =
    		  theCar.get().getOffers().stream()
              .filter(carOffer -> carOffer.getStatus().equals("pending"))
              .collect(Collectors.toList());
      rejectedOffers.remove(acceptedOffer);
      rejectedOffers.forEach(
          rejectedOffer -> offerRepository.updateOfferStatus(rejectedOffer, "rejected"));
      carRepository.updateCarStatus(acceptedOffer.getCar(), "sold");
    } catch (Exception e) {
    	e.printStackTrace();
      System.out.println("Failed to accept offer");
    }
  }

  @Override
  public List<Offer> getAllPendingOffersForUser(User user) {
    var allOffers = offerRepository.getAllOffersForUser(user);
    var allUnacceptedOffers =
        allOffers.stream().filter(offer -> offer.getStatus().equals("pending"));
    return allUnacceptedOffers.collect(Collectors.toList());
  }

  @Override
  public List<Offer> getOffersForCar(Car car) {
    return offerRepository.getOffersForCar(car);
  }

  @Override
  public List<Offer> getOffersMadeByUser(User user) {
    return offerRepository.getAllOffersByUser(user);
  }
}
