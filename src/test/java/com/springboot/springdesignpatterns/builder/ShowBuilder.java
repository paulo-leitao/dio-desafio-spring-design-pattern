package com.springboot.springdesignpatterns.builder;


import com.springboot.springdesignpatterns.model.Show;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShowBuilder {

  @Builder.Default
  private String title = "Titulo Teste";
  
  @Builder.Default
  private String year = "2022";
  
  @Builder.Default
  private String released = "2022";
  
  @Builder.Default
  private String runtime = "60min";
  
  @Builder.Default
  private String genre = "Teste";
  
  @Builder.Default
  private String actors = "John Doe, Jane Doe";

  @Builder.Default
  private String plot = "A test that won't fail";

  @Builder.Default
  private String language = "Java";
  
  @Builder.Default
  private String country = "Brazil";

  @Builder.Default
  private String imdbRating = "10/10";
  
  @Builder.Default
  private String imdbID = "testeid";
  
  @Builder.Default
  private String type = "teste";
  
  @Builder.Default
  private String totalSeasons = "1";
  
  @Builder.Default
  private String website = "github.com/paulo-leitao";

  public Show toShow(){
    return new Show(
      title,
      year,
      released,
      runtime,
      genre,
      actors,
      plot,
      language,
      country,
      imdbRating,
      imdbID,
      type,
      totalSeasons,
      website
    );
  }
}
