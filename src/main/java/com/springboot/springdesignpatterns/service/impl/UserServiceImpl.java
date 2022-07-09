package com.springboot.springdesignpatterns.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.springdesignpatterns.model.User;
import com.springboot.springdesignpatterns.repository.UserRepository;
import com.springboot.springdesignpatterns.service.UserService;

@Service
public class UserServiceImpl implements UserService{

  @Autowired
  private UserRepository userRepository;

  @Override
  public Iterable<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User findUserById(Integer id) {
    Optional<User> user = userRepository.findById(id);
    return user.get();
  }
  
  @Override
  public void addNewUser(User user) {
    userRepository.save(user);
  }
  
  @Override
  public void updateUser(Integer id, User user) {
    Optional<User> userOlder = userRepository.findById(id);
    if (userOlder.isPresent()) {
      userRepository.save(user);
    }
  }

  @Override
  public void deleteUser(Integer id) {
    userRepository.deleteById(id);    
  }
  
}
