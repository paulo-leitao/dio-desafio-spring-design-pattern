package com.springboot.springdesignpatterns.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.springdesignpatterns.builder.ShowBuilder;
import com.springboot.springdesignpatterns.builder.UserBuilder;
import com.springboot.springdesignpatterns.builder.WatchedBuilder;
import com.springboot.springdesignpatterns.exception.BusinessException;
import com.springboot.springdesignpatterns.model.Show;
import com.springboot.springdesignpatterns.model.User;
import com.springboot.springdesignpatterns.repository.ShowRepository;
import com.springboot.springdesignpatterns.repository.UserRepository;
import com.springboot.springdesignpatterns.service.impl.ShowServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {
  
  @Mock
  private ShowRepository showRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private OmdbService omdbService;

  @InjectMocks
  private ShowServiceImpl service;
  
  @Test
  @DisplayName("Should return a list with all the shows on the database.")
  void canFindAllShows() {
    // When
    service.findAllShows();

    // Then
    verify(showRepository).findAll();
  }

  @Test
  @DisplayName("Should be able to find a Show on the database by its Title.")
  void canFindShowByTitle() {
    // Given
    String title = "Test";

    // When
    service.findShowByTitle(title);

    // Then
    verify(showRepository).findByTitleContainingIgnoreCase(title);
  }
  
  @Test
  @DisplayName("Should be able to add a Show found in the database to the User's Watched List.")
  void canAddShowFromDatabaseToUserList() {
    // Given
    Integer id = 1;
    Show show = ShowBuilder.builder().title("Another Show").build().toShow();
    User currentUser = UserBuilder.builder()
        .watched(WatchedBuilder.builder().build().toWatched())
        .build().toUser();

    when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
    when(showRepository.findByTitleContainingIgnoreCase(show.getTitle())).thenReturn(Collections.singletonList(show));

    // doReturn(hasShow).when(serviceImpl).searchForMatch(currentUser, show);
    // doNothing().when(serviceImpl).updateUserWatchedList(currentUser, show, false);
    // when(user.getWatched().getList()).thenReturn(watched);
    // when(onDbShow.stream().findFirst().get()).thenReturn(show);
    
    // When
    service.addShow(id, show);

    // Then
    verify(userRepository).save(currentUser);
  }

  @Test
  @DisplayName("Should be able to retrieve a show's data from a external API if the movie/series is not in the database.")
  void canAddShowFromExternalDatabaseViaApiRequest() {
    // Given
    Integer id = 1;
    Show show = mock(Show.class);
    Show newShow = ShowBuilder.builder().title("New Show").build().toShow();
    User currentUser = UserBuilder.builder()
        .watched(WatchedBuilder.builder().build().toWatched())
        .build().toUser();

    when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
    when(showRepository.findByTitleContainingIgnoreCase(show.getTitle())).thenReturn(Collections.emptyList());
    when(omdbService.findShow(show.getTitle())).thenReturn(newShow);

    // When
    service.addShow(id, show);

    // Then
    verify(userRepository).save(currentUser);

  }

  @Test
  @DisplayName("Should Thrown an exception when trying to add a Show already in the  User's list.")
  void cantAddShowAlreadyInUserList() {
    // Given
    Integer id = 1;
    Show show = ShowBuilder.builder().build().toShow();
    User currentUser = UserBuilder.builder()
        .watched(WatchedBuilder.builder().build().toWatched())
        .build().toUser();

    when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));
    when(showRepository.findByTitleContainingIgnoreCase(show.getTitle())).thenReturn(Collections.singletonList(show));

    // When
    BusinessException businessException = assertThrows(BusinessException.class, () -> {service.addShow(id, show);});

    // Then
    assertThat(businessException.getMessage(), is(equalTo("Movie/series already listed.")));
  }

  @Test
  @DisplayName("Should throw an exception if the user is not found.")
  void cantAddShowBecauseUserNotFound() {
    // Given
    Integer id = 1;
    Show show = mock(Show.class);
    when(userRepository.findById(id)).thenReturn(Optional.empty());

    // When
    BusinessException businessException = assertThrows(BusinessException.class, () -> {service.addShow(id, show);});

    // Then
    assertThat(businessException.getMessage(), is(equalTo("User not found.")));
  }

  @Test
  @DisplayName("Should be able to remove a movie/series from the user's list.")
  void canRemoveShowFromUserList() {
    // Given
    Integer id = 1;
    Show show = ShowBuilder.builder().build().toShow();
    User currentUser = UserBuilder.builder()
        .watched(WatchedBuilder.builder().build().toWatched())
        .build().toUser();

    when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));

    // When
    service.removeShow(id, show);

    // Then
    verify(userRepository).save(currentUser);
  }

  @Test
  @DisplayName("Should throw an exception if the user is not found when trying to remove a movie/series.")
  void canNotRemoveIfUserNotFound() {
    // Given
    Integer id = 1;
    Show show = mock(Show.class);

    when(userRepository.findById(id)).thenReturn(Optional.empty());

    // When
    BusinessException businessException = assertThrows(BusinessException.class, () -> {service.removeShow(id, show);});

    // Then
    assertThat(businessException.getMessage(), is(equalTo("User not found.")));
  }

  @Test
  @DisplayName("Should throw an exception if the user doesn't have the show on his list.")
  void canNotRemoveIfShowNotFoundInUserList() {
    // Given
    Integer id = 1;
    Show show = ShowBuilder.builder().title("Different Movie").build().toShow();
    User currentUser = UserBuilder.builder()
        .watched(WatchedBuilder.builder().build().toWatched())
        .build().toUser();

    when(userRepository.findById(id)).thenReturn(Optional.of(currentUser));

    // When
    BusinessException businessException = assertThrows(BusinessException.class, () -> {service.removeShow(id, show);});

    // Then
    assertThat(businessException.getMessage(), is(equalTo("Show not found in the user's list.")));
  }

}
