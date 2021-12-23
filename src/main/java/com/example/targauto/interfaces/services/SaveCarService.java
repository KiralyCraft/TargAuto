package com.example.targauto.interfaces.services;

import com.example.targauto.models.SavedCar;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface SaveCarService {
  List<SavedCar> getSavedCarsFromCookies(Cookie[] cookies);

  void clearSavedCars(Cookie[] cookies, HttpServletResponse response);
}
