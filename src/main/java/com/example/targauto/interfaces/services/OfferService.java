package com.example.targauto.interfaces.services;

import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;

import java.util.List;
import java.util.Optional;

public interface OfferService {

  Optional<Offer> processUserOffer(User user, Car car, double price);

  void acceptCarOffer(Offer offer);

  List<Offer> getAllPendingOffersForUser(User user);

  List<Offer> getOffersForCar(Car car);

  List<Offer> getOffersMadeByUser(User user);
}
