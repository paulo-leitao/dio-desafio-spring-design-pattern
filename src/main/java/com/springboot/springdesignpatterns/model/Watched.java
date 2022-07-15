package com.springboot.springdesignpatterns.model;

import java.util.ArrayList;

// import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Watched {
  
  // @Column(name="shows_list")
  @ArraySchema
  @Lob
  private ArrayList<Show> list;
  
  public ArrayList<Show> getList() {
    return list;
  }
  public void addToList(Show show) {
    list.add(show);
  }

  public void removeFromList(Show show) {
    list.remove(show);
  }
  
}
