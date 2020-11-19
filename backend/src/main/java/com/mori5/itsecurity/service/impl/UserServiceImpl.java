package com.mori5.itsecurity.service.impl;


import com.mori5.itsecurity.api.model.UserRegistrationDTO;
import com.mori5.itsecurity.api.model.UserUpdateDTO;
import com.mori5.itsecurity.domain.Role;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;
import com.mori5.itsecurity.errorhandling.exception.ConflictException;
import com.mori5.itsecurity.errorhandling.exception.EntityNotFoundException;
import com.mori5.itsecurity.errorhandling.exception.NoUserInContextException;
import com.mori5.itsecurity.mapper.UserMapper;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.security.AuthUserDetails;
import com.mori5.itsecurity.security.AuthenticationFacade;
import com.mori5.itsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String NOT_LOGGED_IN = "There is not logged in user";
    private static final String USERNAME_REGISTERED = "Username is already registered";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Override
    @Transactional
    public User createUser(UserRegistrationDTO newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new ConflictException(USERNAME_REGISTERED, ItSecurityErrors.CONFLICT);
        }

        User user = UserMapper.mapUserRegistrationDTOToUser(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);

        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public User getCurrentUserProfile() {
        return getCurrentUser();
    }

    @Override
    @Transactional
    public User updateCurrentUserProfile(UserUpdateDTO profileDTO) {
        User updatedUser = fillUpdateUser(getCurrentUser(), profileDTO);
        return userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void deleteCurrentUserProfile() {
        User user = getCurrentUser();
        userRepository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public User getCurrentUser() {
        AuthUserDetails currentUserDetails = authenticationFacade.getCurrentUserFromContext();
        if (currentUserDetails != null) {
            return userRepository.findById(currentUserDetails.getUserId()).orElseThrow(() -> new NoUserInContextException(NOT_LOGGED_IN, ItSecurityErrors.UNKNOWN));
        } else {
            throw new NoUserInContextException(NOT_LOGGED_IN, ItSecurityErrors.UNKNOWN);
        }
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional
    public User updateUser(String id, UserUpdateDTO user) {
        User u = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));
        User updateUser = fillUpdateUser(u, user);
        return userRepository.save(updateUser);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void banUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));
        user.setIsBanned(true);
    }

    @Override
    @Transactional
    public void makeAdmin(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND, ItSecurityErrors.ENTITY_NOT_FOUND));
        user.setRole(Role.ADMIN);
    }

    private User fillUpdateUser(User user, UserUpdateDTO profileDTO) {
        if (profileDTO.getLastName() != null) {
            user.setLastName(profileDTO.getLastName());
        }

        if (profileDTO.getFirstName() != null) {
            user.setFirstName(profileDTO.getFirstName());
        }

        if (profileDTO.getUsername() != null) {
            if (userRepository.findFirstByUsernameAndIdNot(profileDTO.getUsername(), user.getId()).isPresent()) {
                throw new ConflictException(USERNAME_REGISTERED, ItSecurityErrors.CONFLICT);
            }
            user.setUsername(profileDTO.getUsername());
        }
        return user;
    }

}
