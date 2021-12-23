package com.example.targauto.managed;

import com.example.targauto.interfaces.services.AuthenticationService;
import com.example.targauto.models.User;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("signInManagedBean")
@ManagedBean
@SessionScoped
public class SignInManagedBean implements Serializable {
  @EJB AuthenticationService authenticationService;

  String email;
  String username;
  String password;
  String confirmationPassword;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmationPassword() {
    return confirmationPassword;
  }

  public void setConfirmationPassword(String confirmationPassword) {
    this.confirmationPassword = confirmationPassword;
  }

  public String createUser() {
    var user = new User(username, email, password);
    // validatenewuser returneaza un mesaj, nu am mai implementat si afisarea lui
    if (user.validateNewUser(confirmationPassword) != null) return "Signin";
    var createdUser = authenticationService.createUser(user);
    if (createdUser.isEmpty()) {
      return "Signin";
    }
    return "Home";
  }
}
