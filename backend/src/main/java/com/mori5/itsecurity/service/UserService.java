package com.mori5.itsecurity.service;


import com.mori5.itsecurity.api.model.UserRegistrationDTO;
import com.mori5.itsecurity.api.model.UserUpdateDTO;
import com.mori5.itsecurity.domain.User;

import java.util.List;

public interface UserService {

    User createUser(UserRegistrationDTO newUser);

    User getCurrentUserProfile();

    User updateCurrentUserProfile(UserUpdateDTO profileDTO);

    void deleteCurrentUserProfile();

    User getCurrentUser();

    List<User> getUsers();

    User getUserById(String id);

    User updateUser(String id, UserUpdateDTO updateUser);

    void deleteUser(String id);

    void makeAdmin(String id);

}
