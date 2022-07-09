package com.springboot.springdesignpatterns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.springdesignpatterns.model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
  List<Show> findByTitleContainingIgnoreCase(String title);
}
