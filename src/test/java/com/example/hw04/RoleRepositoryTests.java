package com.example.hw04;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.example.hw04.entity.Role;
import com.example.hw04.repo.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // not used
@Rollback(false) // not used
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repo;



    @Test
    public void testCreateRoles() {
        Role user = new Role("User");
        Role admin = new Role("Admin");
        Role customer = new Role("Customer");

        repo.saveAll(List.of(user, admin, customer));
        List<Role> listRoles = repo.findAll();
        assertThat(listRoles.size()).isEqualTo(3);
    }
}
