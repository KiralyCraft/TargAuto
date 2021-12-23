package com.example.targauto.utils;

import com.example.targauto.models.User;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class SessionUtils {

  public static HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }

  public static Optional<User> getUser() {
    User user;
    HttpSession session = getSession();
    user = (User) session.getAttribute("user");
    if (user != null) return Optional.of(user);
    else return Optional.empty();
  }

  public static void storeUserInSession(User user) {
    HttpSession session = SessionUtils.getSession();
    session.setAttribute("user", user);
  }
}
