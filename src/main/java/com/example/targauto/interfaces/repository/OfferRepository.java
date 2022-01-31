package com.example.targauto.interfaces.repository;

import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {
  Optional<Offer> createOffer(User user, Car car, double price);

  Optional<Offer> getOfferForCarByUser(User user, Car car);

  List<Offer> getOffersForCar(Car car);

  Optional<Offer> updateOfferPrice(Offer oldOffer, double price);

  void updateOfferStatus(Offer oldOffer, String status);

  List<Offer> getAllOffersForUser(User user);

  List<Offer> getAllOffersByUser(User user);
}
