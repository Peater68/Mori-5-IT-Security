package com.mori5.itsecurity.mapper;


import com.mori5.itsecurity.api.model.ReducedUserDTO;
import com.mori5.itsecurity.api.model.UserDTO;
import com.mori5.itsecurity.api.model.UserRegistrationDTO;
import com.mori5.itsecurity.domain.Role;
import com.mori5.itsecurity.domain.User;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {}

    public static UserDTO mapUserToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .banned(user.getIsBanned())
                .role(UserDTO.RoleEnum.valueOf(user.getRole().toString()))
                .createdAt(user.getCreatedAt().atOffset(ZoneOffset.UTC))
                .updatedAt(user.getUpdatedAt().atOffset(ZoneOffset.UTC))
                .build();
    }

    public static List<UserDTO> mapUsersListToUsersDTOList(List<User> users) {
        return users.stream().map(UserMapper::mapUserToUserDTO).collect(Collectors.toList());
    }

    public static User mapUserRegistrationDTOToUser(UserRegistrationDTO userRegistrationDTO) {
        return User.builder()
                .firstName(userRegistrationDTO.getFirstName())
                .email(userRegistrationDTO.getEmail())
                .lastName(userRegistrationDTO.getLastName())
                .username(userRegistrationDTO.getUsername())
                .password(userRegistrationDTO.getPassword())
                .isBanned(false)
                .build();
    }

    public static ReducedUserDTO mapUserToReducedUserDTO(User user) {
        return ReducedUserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }

}
