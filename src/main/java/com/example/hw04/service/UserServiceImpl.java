package com.example.hw04.service;


import com.example.hw04.conf.JwtService;
import com.example.hw04.dto.AuthenticationResponse;
import com.example.hw04.dto.UserDto;
import com.example.hw04.entity.AppUser;
import com.example.hw04.entity.Role;
import com.example.hw04.repo.RoleRepository;
import com.example.hw04.repo.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public AuthenticationResponse saveUser(UserDto userDto) {
        AppUser user = new AppUser();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.addRole(role);
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

//    public AuthenticationResponse authenticate(AuthenticationR)

    @Override
    public Optional<AppUser> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<AppUser> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(AppUser user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        Set<Role> roles = user.getRoles();
        userDto.setRoles(roles);
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
