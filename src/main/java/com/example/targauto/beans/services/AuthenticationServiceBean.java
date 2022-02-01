package com.example.targauto.beans.services;


import com.example.targauto.interfaces.repository.UserRepository;
import com.example.targauto.interfaces.services.AuthenticationService;
import com.example.targauto.models.User;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;

import java.util.Optional;

@Stateless
@Local(AuthenticationService.class)
public class AuthenticationServiceBean implements AuthenticationService {
  @EJB private UserRepository usersRepository;

  @Override
  public Optional<User> getUserByUsername(String username) {
    return usersRepository.getUserByUsername(username);
  }

  @Override
  public Optional<User> verifyUser(Optional<User> userFromDatabase, String usernameInput, String passwordInput) {
    if(userFromDatabase.isEmpty())
      return Optional.empty();
    if (userFromDatabase.get().getUsername().equals(usernameInput) && userFromDatabase.get().getPassword().equals(passwordInput))
      return userFromDatabase;
    return Optional.empty();
  }

  @Override
  public Optional<User> createUser(User user) {
    Optional<User> checkUser =
        usersRepository.verifyUniqueNameAndEmail(user.getUsername(), user.getEmail());

    if (checkUser.isPresent()) return Optional.empty();

    return usersRepository.createUser(user);
  }

	@Override
	public boolean removeUser(User user)
	{
		if (getUserByUsername(user.getUsername()).isPresent())
		{
			usersRepository.removeUser(user);
			return true;
		}
		else
		{
			return false;
		}
	}
}
