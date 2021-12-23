package com.example.targauto.interfaces.repository;


import com.example.targauto.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> verifyUniqueNameAndEmail(String username, String email);

    Optional<User> getUserByUsername(String username);

    Optional<User> createUser(User user);

}
