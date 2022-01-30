package com.example.targauto.interfaces.services;

import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;

import java.util.Optional;

public interface OfferService {

  Optional<Offer> processUserOffer(User user, Car car, double price);

  boolean acceptUserOffer(User user, Offer offer);
}
