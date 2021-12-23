package com.example.targauto.beans.repository;

import com.example.targauto.interfaces.repository.OfferRepository;
import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@Stateless
@Local(OfferRepository.class)
public class OfferRepositoryBean implements OfferRepository {
  @PersistenceContext(unitName = "targAuto")
  private EntityManager manager;

  @Override
  public Optional<Offer> createOffer(User user, String address, Car car) {
    Offer command = new Offer(car, address);
    user.addOffer(command);
    manager.persist(command);

    return Optional.of(command);
  }
}
