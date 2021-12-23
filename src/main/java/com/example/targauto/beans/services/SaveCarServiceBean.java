package com.example.targauto.beans.services;

import com.example.targauto.interfaces.repository.CarRepository;
import com.example.targauto.interfaces.services.SaveCarService;
import com.example.targauto.models.SavedCar;
import com.example.targauto.models.SavedCarCookie;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
@Local(SaveCarService.class)
public class SaveCarServiceBean implements SaveCarService {
  @EJB CarRepository carRepository;

  @Override
  public List<SavedCar> getSavedCarsFromCookies(Cookie[] cookies) {
    var savedCarsCookies = filterCookiesForSavedCars(cookies);

    List<SavedCarCookie> savedCars = getCarIdFromCookie(savedCarsCookies);

    return getSavedCars(savedCars);
  }

  private List<SavedCarCookie> getCarIdFromCookie(List<Cookie> cartCookies) {
    return cartCookies.stream()
        .map(cookie -> new SavedCarCookie(cookie.getName().replace("saved-car-", "")))
        .collect(Collectors.toList());
  }

  private List<Cookie> filterCookiesForSavedCars(Cookie[] cookies) {
    return Arrays.stream(cookies)
        .filter(cookie -> cookie.getName().startsWith("saved-car-"))
        .collect(Collectors.toList());
  }

  private List<SavedCar> getSavedCars(List<SavedCarCookie> savedCar) {
    return savedCar.stream()
        .map(getSavedCarsOrNull())
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private Function<SavedCarCookie, SavedCar> getSavedCarsOrNull() {
    return cartProduct -> {
      var product = carRepository.getCarById(cartProduct.getCarId());
      return product.map(SavedCar::new).orElse(null);
    };
  }

  @Override
  public void clearSavedCars(Cookie[] cookies, HttpServletResponse response) {
    filterCookiesForSavedCars(cookies)
        .forEach(
            cookie -> {
              cookie.setMaxAge(0);
              response.addCookie(cookie);
            });
  }
}
