package com.springboot.springdesignpatterns.controller;

import static com.springboot.springdesignpatterns.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.springboot.springdesignpatterns.builder.UserBuilder;
import com.springboot.springdesignpatterns.model.User;
import com.springboot.springdesignpatterns.service.UserService;

// TODO Tests for Exceptions cases
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  private static final String API_URL_PATH = "/user";
  private static final int VALID_USER_ID = 1;
  private static final int INVALID_USER_ID = 2;

  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController controller;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  @DisplayName("Must return Status 200 when GET method for all users is called.")
  void whenGETListWithAllUsersIsCalledThenOkStatusIsReturned() throws Exception {
    // Given
    User user = UserBuilder.builder().build().toUser();


    // When
    when(userService.findAllUsers()).thenReturn(Collections.singletonList(user));

    // Then
    mockMvc.perform(get(API_URL_PATH)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(user.getId())))
        .andExpect(jsonPath("$[0].name", is(user.getName())))
        .andExpect(jsonPath("$[0].watched", is(user.getWatched())));
  }

  @Test
  @DisplayName("Must return a User when find user by id is requested.")
  void whenGETUserByIdIsCalledThenItMustReturnUserWithStatusOk() throws Exception {
    // Given
    User user = UserBuilder.builder().build().toUser();

    // When
    when(userService.findUserById(VALID_USER_ID)).thenReturn(user);

    // Then
    mockMvc.perform(get(API_URL_PATH+"/"+VALID_USER_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(user.getId())))
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.watched", is(user.getWatched())));
  }

  @Test
  @DisplayName("Must return status ok when a new user is added to de database.")
  void whenAddNewUserOfTypePOSTisCalledThenItMustReturnStatusOk() throws Exception {
    // Given
    User user = UserBuilder.builder().build().toUser();

    // When
    doNothing().when(userService).addNewUser(any(User.class));

    // Then
    mockMvc.perform(post(API_URL_PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(user.getId())))
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.watched", is(user.getWatched())));
  }

  @Test
  @DisplayName("Must return Status Ok when update some user data with success.")
  void whenUpdateUserOfTypePUTMethodIsCalledThenItMustReturnStatusOk() throws Exception {
    // Given
    User user = UserBuilder.builder().build().toUser();
  
    // When
    doNothing().when(userService).updateUser(anyInt(), any(User.class));

    // Then
    mockMvc.perform(put(API_URL_PATH+"/"+VALID_USER_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(user))).andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(user.getId())))
        .andExpect(jsonPath("$.name", is(user.getName())))
        .andExpect(jsonPath("$.watched", is(user.getWatched())));
  }

  @Test
  @DisplayName("Must return status Ok when a user is deleted with success from database.")
  void whenDeleteUserOfTypeDELETEisCalledThenStatusOkMustBeReturnedWithoutBody() throws Exception {
    // When
    doNothing().when(userService).deleteUser(anyInt());

    // Then
    mockMvc.perform(delete(API_URL_PATH+"/"+VALID_USER_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
