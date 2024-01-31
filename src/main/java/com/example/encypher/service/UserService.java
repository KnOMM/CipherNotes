package com.example.encypher.service;

import com.example.encypher.dto.AuthenticationResponse;
import com.example.encypher.dto.UserDto;
import com.example.encypher.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    AuthenticationResponse saveUser(UserDto userDto);

    Optional<AppUser> findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
