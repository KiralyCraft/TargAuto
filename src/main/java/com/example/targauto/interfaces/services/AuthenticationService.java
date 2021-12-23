package com.example.targauto.interfaces.services;


import com.example.targauto.models.User;

import java.util.Optional;

public interface AuthenticationService {

    Optional<User> getUserByUsername(String username);

    Optional<User> verifyUser(Optional<User> user, String username, String password);

    Optional<User> createUser(User user);
}
