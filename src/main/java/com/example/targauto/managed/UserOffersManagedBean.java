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

@Named("userOffersManagedBean")
@ManagedBean
@SessionScoped
public class UserOffersManagedBean implements Serializable {
  @EJB OfferService offerService;

  public List<Offer> allOffersMadeByUser(Optional<User> user) {
    if (user.isEmpty()) return List.of();
    return offerService.getOffersMadeByUser(user.get());
  }
}
