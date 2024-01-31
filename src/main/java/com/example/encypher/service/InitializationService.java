package com.example.encypher.service;

import com.example.encypher.entity.AppUser;
import com.example.encypher.entity.Caesar;
import com.example.encypher.entity.Role;
import com.example.encypher.repo.CaesarRepository;
import com.example.encypher.repo.RoleRepository;
import com.example.encypher.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
