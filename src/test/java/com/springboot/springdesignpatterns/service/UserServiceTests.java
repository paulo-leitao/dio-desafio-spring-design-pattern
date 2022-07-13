package com.springboot.springdesignpatterns.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.springdesignpatterns.exception.BusinessException;
import com.springboot.springdesignpatterns.model.User;
import com.springboot.springdesignpatterns.repository.UserRepository;
import com.springboot.springdesignpatterns.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  UserServiceImpl service;

  @Test
  @DisplayName("Should receive a list with all the users on the database.")
  void canGetAllUsers() {
    // When
    service.findAllUsers();

    // Then
    verify(userRepository).findAll();
  }

  @Test
  @DisplayName("Should receive the User data by providing the user id.")
  void canGetUserById() {
    // Given 
    Integer id = 1;
    User user = mock(User.class);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    // When
    service.findUserById(id);

    // Then
    verify(userRepository).findById(id);
  }

  @Test
  @DisplayName("Must be able to create a new User.")
  void canCreateNewUser() {
    // GIven
    User user = mock(User.class);

    // When
    service.addNewUser(user);

    // Then
    verify(userRepository).save(user);
  }

  @Test
  @DisplayName("Should update an existing user.")
  void canUpdateExistingUser() {
    // Given
    Integer id = 1;
    User user =mock(User.class);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    // When
    service.updateUser(id, user);

    // Then
    verify(userRepository).save(user);
  }

  @Test
  @DisplayName("Should throw an exception if the user is not found after an update request")
  void cantUpdateUserNotFount() {
    // Given
    Integer id = 1;
    User user =mock(User.class);

    when(userRepository.findById(id)).thenReturn(Optional.empty());

    // When
    BusinessException businessException = assertThrows(BusinessException.class, () -> { service.updateUser(id, user); });
  
    // Then
    assertThat(businessException.getMessage(), is(equalTo("Usuário não encontrado.")));
  }

  @Test
  @DisplayName("It has to be able to delete a User by passing their id.")
  void ShouldDeleteSpecificUser() {
    // Given
    Integer id = 1;

    // When
    service.deleteUser(id);

    // Then
    verify(userRepository).deleteById(id);
  }
}
