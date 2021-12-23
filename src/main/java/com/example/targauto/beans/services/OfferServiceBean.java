package com.example.targauto.beans.services;


import com.example.targauto.interfaces.repository.OfferRepository;
import com.example.targauto.interfaces.repository.CarRepository;
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
  @EJB
  OfferRepository commandRepository;

  @EJB()
  CarRepository carRepository;

  @Override
  public Optional<Offer> createUserOffer(User user, String address, Car car) {
    return commandRepository.createOffer(user, address, car);
  }

  @Override
  public boolean acceptUserOffer(User user, Offer offer) {
    return false;
  }

}
