package com.example.targauto.models;

import java.util.UUID;

public class SavedCar {
    private final String savedCarId;
    private final Car car;

    public SavedCar(Car product) {
        this.savedCarId = UUID.randomUUID().toString();
        this.car = product;
    }

    public Car getCar() {
        return car;
    }

    public String getSavedCarId() {
        return savedCarId;
    }

}
