package com.springboot.springdesignpatterns.builder;

import java.util.ArrayList;

import com.springboot.springdesignpatterns.model.Show;
import com.springboot.springdesignpatterns.model.Watched;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WatchedBuilder {
  
  @Builder.Default
  private ArrayList<Show> list = new ArrayList<Show>(){{
    add(ShowBuilder.builder().build().toShow());
  }};

  public Watched toWatched(){
    return new Watched(list);
  }
  
}
