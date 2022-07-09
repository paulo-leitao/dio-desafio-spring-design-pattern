package com.springboot.springdesignpatterns.service;

import com.springboot.springdesignpatterns.model.User;

public interface UserService {
  Iterable<User> findAllUsers();

  User findUserById(Integer id);

  void addNewUser(User user);

  void updateUser(Integer id, User user);

  void deleteUser(Integer id);
}
