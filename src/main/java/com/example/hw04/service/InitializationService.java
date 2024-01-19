package com.example.hw04.service;

import com.example.hw04.entity.AppUser;
import com.example.hw04.entity.Caesar;
import com.example.hw04.entity.Role;
import com.example.hw04.repo.CaesarRepository;
import com.example.hw04.repo.RoleRepository;
import com.example.hw04.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class InitializationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CaesarRepository caesarRepository;

    @PostConstruct
    private void initAppData() {

        AppUser user = new AppUser();
        user.setName("admin admin");
        user.setEmail("admin@admin.com");
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode("admin"));


        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.addRole(role);
        userRepository.save(user);

        Caesar caesar1 = new Caesar();
        caesar1.setText("laskdjfk");
        caesar1.setPass(2);
        caesar1.setUser(user);
        caesarRepository.save(caesar1);

        Caesar caesar2 = new Caesar();
        caesar2.setText("laskdjfk");
        caesar2.setPass(2);
        caesar2.setUser(user);
        caesarRepository.save(caesar2);

    }


    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
