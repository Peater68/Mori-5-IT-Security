package com.mori5.itsecurity.controller;

import com.mori5.itsecurity.api.UserApi;
import com.mori5.itsecurity.api.model.UserDTO;
import com.mori5.itsecurity.api.model.UserRegistrationDTO;
import com.mori5.itsecurity.api.model.UserUpdateDTO;
import com.mori5.itsecurity.mapper.UserMapper;
import com.mori5.itsecurity.security.AuthoritiesConstants;
import com.mori5.itsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<UserDTO> createUser(@Valid UserRegistrationDTO userRegistrationDTO) {
        return ResponseEntity.ok(UserMapper.mapUserToUserDTO(userService.createUser(userRegistrationDTO)));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<Void> deleteMe() {
        userService.deleteCurrentUserProfile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN})
    public ResponseEntity<Void> deleteUserById(String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<UserDTO> getMe() {
        return ResponseEntity.ok(UserMapper.mapUserToUserDTO((userService.getCurrentUserProfile())));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN})
    public ResponseEntity<UserDTO> getUserById(String userId) {
        return ResponseEntity.ok(UserMapper.mapUserToUserDTO((userService.getUserById(userId))));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN})
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(UserMapper.mapUsersListToUsersDTOList((userService.getUsers())));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN})
    public ResponseEntity<Void> makeUserAdmin(String userId) {
        userService.makeAdmin(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<UserDTO> updateMe(@Valid UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(UserMapper.mapUserToUserDTO((userService.updateCurrentUserProfile(userUpdateDTO))));
    }

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN})
    public ResponseEntity<UserDTO> updateUserById(String userId, @Valid UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(UserMapper.mapUserToUserDTO((userService.updateUser(userId, userUpdateDTO))));
    }
}
