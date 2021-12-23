package com.example.targauto.interfaces.repository;



import com.example.targauto.models.Car;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;

import java.util.Optional;

public interface OfferRepository {
    Optional<Offer> createOffer(User user, String address, Car car);
}
