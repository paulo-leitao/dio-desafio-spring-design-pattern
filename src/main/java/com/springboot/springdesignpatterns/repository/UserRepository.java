package com.springboot.springdesignpatterns.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.springdesignpatterns.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  
}
