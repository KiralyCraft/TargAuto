package com.example.targauto.beans.services;

import com.example.targauto.interfaces.repository.OfferRepository;
import com.example.targauto.interfaces.services.OfferService;
import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

import java.util.Optional;

@Stateless
@Local(OfferService.class)
public class OfferServiceBean implements OfferService {
  @EJB OfferRepository offerRepository;

  @Override
  public Optional<Offer> processUserOffer(User user, Car car, double price) {
    var offerMadeByUser = offerRepository.getUserOfferForCar(user, car);
    if (offerMadeByUser.isPresent())
      return offerRepository.updateOffer(offerMadeByUser.get(), price);
    return offerRepository.createOffer(user, car, price);
  }

  @Override
  public boolean acceptUserOffer(User user, Offer offer) {
    return false;
  }
}
