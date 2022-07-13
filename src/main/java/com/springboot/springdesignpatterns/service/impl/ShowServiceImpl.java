package com.springboot.springdesignpatterns.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.springboot.springdesignpatterns.dto.ShowDTO;
import com.springboot.springdesignpatterns.model.Show;
import com.springboot.springdesignpatterns.model.User;
import com.springboot.springdesignpatterns.model.Watched;
import com.springboot.springdesignpatterns.repository.ShowRepository;
import com.springboot.springdesignpatterns.repository.UserRepository;
import com.springboot.springdesignpatterns.service.OmdbService;
import com.springboot.springdesignpatterns.service.ShowService;

@Service
public class ShowServiceImpl implements ShowService{

  @Autowired
  private ShowRepository showRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OmdbService omdbService;

  @Override
  public Iterable<Show> findAllShows() {
    return showRepository.findAll();
  }

  @Override
  public Iterable<Show> findShowByTitle(String title) {
    return showRepository.findByTitleContainingIgnoreCase(title);
  }

  @Override
  public Show addShow(Integer user_id, Show show) {
    Optional<User> user = userRepository.findById(user_id);
    if (user.isPresent()) {
      User currentUser = user.get();
      List<Show> onDbShow = showRepository.findByTitleContainingIgnoreCase(show.getTitle());
      if (!onDbShow.isEmpty()) {
        boolean hasShow = searchForMatch(currentUser, show);
        if (!hasShow) {
          Show newShow = onDbShow.stream().findFirst().get();
          updateUserWatchedList(currentUser, newShow, false);
          return newShow;
        }
      } else {
        Show newShow = omdbService.findShow(show.getTitle());
        showRepository.save(newShow);
        updateUserWatchedList(currentUser, newShow, false);
        return newShow;
      }
    }
    return null;
  }

  
  @Override
  public void removeShow(Integer user_id, Show show) {
    Optional<User> user = userRepository.findById(user_id);
    if (user.isPresent()) {
      User currentUser = user.get();
      boolean hasShow = searchForMatch(currentUser, show);
      if (hasShow) {
        List<Show> watched = currentUser.getWatched().getList();
        Show getFromList = watched.stream()
            .filter(userShow -> (userShow.getTitle() == null)
                ? false
                : userShow.getTitle().toLowerCase()
                    .contains(show.getTitle().toLowerCase()))
            .findFirst().get();
        updateUserWatchedList(currentUser, getFromList, true);
      }
    }
  }
  
  private boolean searchForMatch(User currentUser, Show show) {
    List<Show> watched = currentUser.getWatched().getList();
    boolean hasShow = watched.stream().anyMatch(userShow -> (userShow.getTitle() == null) ? false
        : userShow.getTitle().toLowerCase().contains(show.getTitle().toLowerCase()));
    return hasShow;
  }

  /**
   * This method update the user watched list, if <pre>"remove"</pre> was set to <pre>true</pre>
   * it means that we want to remove a show from the list, if set to <pre>false</pre> it will add to the list.
   * 
   * @param currentUser
   * @param newShow
   * @param remove
   */
  private void updateUserWatchedList(User currentUser, Show newShow, boolean remove) {
    Watched userList = currentUser.getWatched();
    if( remove ){
      userList.removeFromList(newShow);
    } else {
      userList.addToList(newShow);
    }
    currentUser.setWatched(userList);
    userRepository.save(currentUser);
  }
}
