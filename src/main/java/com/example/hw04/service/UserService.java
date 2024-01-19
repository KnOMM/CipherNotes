package com.example.hw04.service;

import com.example.hw04.dto.AuthenticationResponse;
import com.example.hw04.dto.UserDto;
import com.example.hw04.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    AuthenticationResponse saveUser(UserDto userDto);

    Optional<AppUser> findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
