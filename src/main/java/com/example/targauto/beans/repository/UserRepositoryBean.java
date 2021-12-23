package com.example.targauto.beans.repository;


import com.example.targauto.interfaces.repository.UserRepository;
import com.example.targauto.models.User;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

@Stateless
@Local(UserRepository.class)
public class UserRepositoryBean implements UserRepository {
  @PersistenceContext(unitName = "targAuto")
  private EntityManager manager;

  @Override
  public Optional<User> verifyUniqueNameAndEmail(String username, String email) {
    try {
      TypedQuery<User> query =
          manager.createQuery(
              "select p from  User p where p.username=:username and p.email=:email", User.class);
      query.setParameter("username", username);
      query.setParameter("email", email);

      return query.getResultStream().findFirst();
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    try {
      TypedQuery<User> query =
          manager.createQuery("select p from  User p where p.username=:username", User.class);
      query.setParameter("username", username);

      var user = query.getSingleResult();

      return Optional.of(user);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> createUser(User user) {
    try {
      manager.persist(user);
      return Optional.of(user);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return Optional.empty();
    }
  }
}
