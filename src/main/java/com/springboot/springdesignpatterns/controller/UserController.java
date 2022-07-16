package com.springboot.springdesignpatterns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.springdesignpatterns.model.User;
import com.springboot.springdesignpatterns.service.UserService;

// TODO Exception Handling
@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  public ResponseEntity<Iterable<User>> findAllUsers() {
    return ResponseEntity.ok(service.findAllUsers());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<User> findUserById(@PathVariable Integer id) {
    return ResponseEntity.ok(service.findUserById(id));
  }

  @PostMapping
  public ResponseEntity<User> addNewUser(@RequestBody User user) {
    service.addNewUser(user);
    return ResponseEntity.ok(user);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Integer id,@RequestBody User user) {
    service.updateUser(id, user);
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
    service.deleteUser(id);
    return ResponseEntity.ok().build();
  }
}
