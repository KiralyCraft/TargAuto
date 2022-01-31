package com.example.targauto.managed;

import com.example.targauto.interfaces.services.OfferService;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named("carsOffersManagedBean")
@ManagedBean
@SessionScoped
public class CarsOffersManagedBean implements Serializable {
  @EJB OfferService offerService;

  public List<Offer> getAllOffersForUserCars(Optional<User> user) {
    if (user.isEmpty()) return List.of();
    return offerService.getAllPendingOffersForUser(user.get());
  }

  public void acceptOffer(Offer offer) {
    offerService.acceptCarOffer(offer);
  }
}
