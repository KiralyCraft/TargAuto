package com.example.targauto.managed;

import com.example.targauto.interfaces.services.AuthenticationService;
import com.example.targauto.models.User;
import com.example.targauto.utils.SessionUtils;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.Optional;

@Named("loggedInManagedBean")
@ManagedBean
@SessionScoped
public class LoggedInManagedBean implements Serializable {
  @EJB AuthenticationService authenticationService;
  private String username;
  private String password;
  private String message;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUsername() {
    if (username == null) return "";
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  // validate login
  public String validateUsernamePassword() {
    Optional<User> userFromDatabase = authenticationService.getUserByUsername(username);
    var user = authenticationService.verifyUser(userFromDatabase, username, password);
    if (user.isPresent()) {
      SessionUtils.storeUserInSession(user.get());
      return "Home";
    } else {
      setMessage("Login failed");
      return "Login";
    }
  }

  public boolean userLoggedIn() {
    return SessionUtils.getUser().isPresent();
  }

  // logout event, invalidate session
  public String logout() {
    HttpSession session = SessionUtils.getSession();
    session.invalidate();
    return "Login";
  }
}
