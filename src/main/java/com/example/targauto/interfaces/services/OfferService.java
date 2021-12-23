package com.example.targauto.interfaces.services;



import com.example.targauto.models.Car;
import com.example.targauto.models.SavedCar;
import com.example.targauto.models.Offer;
import com.example.targauto.models.User;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    Optional<Offer> createUserOffer(User user, String address, Car car);

    boolean acceptUserOffer(User user,Offer offer);
}
