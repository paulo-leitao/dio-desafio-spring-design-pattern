package com.springboot.springdesignpatterns.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tab_shows")
public class Show implements Serializable{

  @JsonProperty("Title")
  private String title;
  
  @Column(name="`year`")
  @JsonProperty("Year")
  private String year;
  
  @JsonProperty("Released")
  private String released;
  
  @JsonProperty("Runtime")
  private String runtime;
  
  @JsonProperty("Genre")
  private String genre;
  
  @JsonProperty("Actors")
  private String actors;
  
  @JsonProperty("Plot")
  private String plot;
  
  @JsonProperty("Language")
  private String language;
  
  @JsonProperty("Country")
  private String country;
  private String imdbRating;
  
  @Id
  @Column(name="imdab_id")
  private String imdbID;
  
  @JsonProperty("Type")
  private String type;
  
  @JsonProperty("TotalSeasons")
  private String totalSeasons;
  
  @JsonProperty("Website")
  private String website;

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getYear() {
    return year;
  }
  public void setYear(String year) {
    this.year = year;
  }
  public String getReleased() {
    return released;
  }
  public void setReleased(String released) {
    this.released = released;
  }
  public String getRuntime() {
    return runtime;
  }
  public void setRuntime(String runtime) {
    this.runtime = runtime;
  }
  public String getGenre() {
    return genre;
  }
  public void setGenre(String genre) {
    this.genre = genre;
  }
  public String getActors() {
    return actors;
  }
  public void setActors(String actors) {
    this.actors = actors;
  }
  public String getPlot() {
    return plot;
  }
  public void setPlot(String plot) {
    this.plot = plot;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getImdbRating() {
    return imdbRating;
  }
  public void setImdbRating(String imdbRating) {
    this.imdbRating = imdbRating;
  }
  public String getImdbID() {
    return imdbID;
  }
  public void setImdbID(String imdbID) {
    this.imdbID = imdbID;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getTotalSeasons() {
    return totalSeasons;
  }
  public void setTotalSeasons(String totalSeasons) {
    this.totalSeasons = totalSeasons;
  }
  public String getWebsite() {
    return website;
  }
  public void setWebsite(String website) {
    this.website = website;
  }
}
