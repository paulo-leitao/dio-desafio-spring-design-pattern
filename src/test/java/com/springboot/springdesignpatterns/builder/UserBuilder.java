package com.springboot.springdesignpatterns.builder;


import com.springboot.springdesignpatterns.model.User;
import com.springboot.springdesignpatterns.model.Watched;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserBuilder {

  @Builder.Default
  private Integer id = 1;

  @Builder.Default
  private String name = "Teste User";

  @Builder.Default
  private Watched watched = WatchedBuilder.builder().build().toWatched();

  public User toUser() {
    return new User(id, name, watched);
  }
}
