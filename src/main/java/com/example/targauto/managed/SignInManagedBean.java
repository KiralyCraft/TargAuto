package com.example.targauto.managed;

import com.example.targauto.interfaces.services.AuthenticationService;
import com.example.targauto.models.User;
import com.example.targauto.utils.SessionUtils;

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
	// las ca fac eu
	
	String userValidationResult = user.validateNewUser(confirmationPassword);
	if (userValidationResult != null)
	{
		String feedbackMessage = "Unknown error";
		switch(userValidationResult)
		{
			case "username_length":
				feedbackMessage = "Username is too short."; break;
			case "password_length":
				feedbackMessage = "Password is too short."; break;
			case "conform_length":
			case "confirm":
				feedbackMessage = "Passwords do not match."; break;
			case "email":
				feedbackMessage = "Email is invalid."; break;
		}
		SessionUtils.queueUserFeedback(feedbackMessage);
		return null; //Merge cu null ca ramanem tot aici
	}
	var createdUser = authenticationService.createUser(user);
	if (createdUser.isEmpty())
	{
		return null; //Same
	}
	return "Home";
  }
}
