package com.springboot.springdesignpatterns.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// import com.springboot.springdesignpatterns.dto.ShowDTO;
import com.springboot.springdesignpatterns.model.Show;

@FeignClient(name="omdb", url="https://www.omdbapi.com/")
public interface OmdbService {
  
  @GetMapping("?t={Title}&apikey=${omdb.key}")
  Show findShow(@PathVariable("Title") String title);
}
