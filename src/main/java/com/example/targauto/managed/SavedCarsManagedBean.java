package com.example.targauto.managed;

import com.example.targauto.interfaces.services.SaveCarService;
import com.example.targauto.models.SavedCar;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionScoped
@ManagedBean
@Named("savedCarsManagedBean")
public class SavedCarsManagedBean implements Serializable {
  @EJB SaveCarService saveCarService;

  public void saveCar(String carId) throws UnsupportedEncodingException {
    Map<String, Object> properties = new HashMap<>();
    properties.put("maxAge", 31536000);
    FacesContext.getCurrentInstance()
        .getExternalContext()
        .addResponseCookie(
            "saved-car-" + carId, URLEncoder.encode(carId, StandardCharsets.UTF_8), properties);
  }

  public String removeSavedCar(String carId) throws UnsupportedEncodingException {
    Map<String, Object> properties = new HashMap<>();
    properties.put("maxAge", 0);
    FacesContext.getCurrentInstance()
        .getExternalContext()
        .addResponseCookie(
            "saved-car-" + carId, URLEncoder.encode(carId, StandardCharsets.UTF_8), properties);
    return "SavedCars?faces-redirect=true";
  }

  public List<SavedCar> getSavedCars() {
    var cookiesMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
    Cookie[] cookies =
        cookiesMap.entrySet().stream()
            .filter(stringObjectEntry -> stringObjectEntry.getKey().startsWith("saved-car-"))
            .map(stringObjectEntry -> (Cookie) stringObjectEntry.getValue())
            .toArray(Cookie[]::new);
    return saveCarService.getSavedCarsFromCookies(cookies);
  }
}
