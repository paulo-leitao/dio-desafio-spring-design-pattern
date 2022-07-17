package com.springboot.springdesignpatterns.controller;

import static com.springboot.springdesignpatterns.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springboot.springdesignpatterns.builder.ShowBuilder;
import com.springboot.springdesignpatterns.model.Show;
import com.springboot.springdesignpatterns.service.ShowService;

@ExtendWith(MockitoExtension.class)
public class ShowControllerTest {

  private static final String API_URL_PATH = "/show";
  private static final String VALID_TITLE = "Teste";

  @Mock
  private ShowService service;

  @InjectMocks
  private ShowController controller;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  @DisplayName("Must return all registers in the database and status ok")
  void whenFindAllShowsIsCalledThenItMustReturnAllRegistersAndStatusOk() throws Exception {
    // Given
    Show show = ShowBuilder.builder().build().toShow();

    // When
    when(service.findAllShows()).thenReturn(Collections.singletonList(show));

    // Then
    mockMvc.perform(get(API_URL_PATH)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].imdbID", is(show.getImdbID())))
        .andExpect(jsonPath("$[0].Title", is(show.getTitle())))
        .andExpect(jsonPath("$[0].Year", is(show.getYear())))
        .andExpect(jsonPath("$[0].Type", is(show.getType())))
        .andExpect(jsonPath("$[0].Genre", is(show.getGenre())))
        .andExpect(jsonPath("$[0].Actors", is(show.getActors())))
        .andExpect(jsonPath("$[0].Country", is(show.getCountry())))
        .andExpect(jsonPath("$[0].Language", is(show.getLanguage())))
        .andExpect(jsonPath("$[0].Plot", is(show.getPlot())))
        .andExpect(jsonPath("$[0].Released", is(show.getReleased())))
        .andExpect(jsonPath("$[0].Runtime", is(show.getRuntime())))
        .andExpect(jsonPath("$[0].TotalSeasons", is(show.getTotalSeasons())))
        .andExpect(jsonPath("$[0].imdbRating", is(show.getImdbRating())));
  }

  @Test
  @DisplayName("Must Return a list of shows with matching titles and status Ok.")
  void whenFindShowByTitleisCalledThenItMustReturnListWithMatchingTitles() throws Exception {
    // Given
    Show show = ShowBuilder.builder().build().toShow();

    // When
    when(service.findShowByTitle(VALID_TITLE)).thenReturn(Collections.singletonList(show));

    // Then
    mockMvc.perform(get(API_URL_PATH+"/"+VALID_TITLE)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(show)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].imdbID", is(show.getImdbID())))
        .andExpect(jsonPath("$[0].Title", is(show.getTitle())))
        .andExpect(jsonPath("$[0].Year", is(show.getYear())))
        .andExpect(jsonPath("$[0].Type", is(show.getType())))
        .andExpect(jsonPath("$[0].Genre", is(show.getGenre())))
        .andExpect(jsonPath("$[0].Actors", is(show.getActors())))
        .andExpect(jsonPath("$[0].Country", is(show.getCountry())))
        .andExpect(jsonPath("$[0].Language", is(show.getLanguage())))
        .andExpect(jsonPath("$[0].Plot", is(show.getPlot())))
        .andExpect(jsonPath("$[0].Released", is(show.getReleased())))
        .andExpect(jsonPath("$[0].Runtime", is(show.getRuntime())))
        .andExpect(jsonPath("$[0].TotalSeasons", is(show.getTotalSeasons())))
        .andExpect(jsonPath("$[0].imdbRating", is(show.getImdbRating())));
  }

  @Test
  @DisplayName("Must Return Status Ok when adding a new show to user list.")
  void whenPOSTaddShowIsCalledThenItMustRetunStatusOk() throws Exception {
    // Given
    Show show = ShowBuilder.builder().build().toShow();
    // Show addedShow = ShowBuilder.builder()
    //     .title("Another Title")
    //     .imdbID("differentId").build().toShow();

    // When
    // doReturn(addedShow).when(service).addShow(eq(1), any(Show.class));

    // Then
    mockMvc.perform(post(API_URL_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(show)))
        .andExpect(status().isOk());
  }

  @Test
  void whenREMOVEshowIsCalledThenItMustRemoveShowFromUserList() throws Exception {
    Show show = ShowBuilder.builder().build().toShow();

    // doNothing().when(service).removeShow(anyInt(), any(Show.class));

    mockMvc.perform(put(API_URL_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(show)))
        .andExpect(status().isOk());
  }
}
