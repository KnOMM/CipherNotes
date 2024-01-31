//package com.example.encypher;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.example.encypher.entity.AppUser;
//import com.example.encypher.entity.Role;
//import com.example.encypher.repo.RoleRepository;
//import com.example.encypher.repo.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.List;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
//public class UserRepositoryTests {
//
//    @Autowired
//    private TestEntityManager entityManager;
//    @Autowired
//    private UserRepository userRepo;
//    @Autowired
//    private RoleRepository roleRepo;
//
//    @Test
//    public void testCreateUser() {
//        AppUser user = new AppUser();
//        user.setEmail("test@gmail.com");
//        user.setPassword("test");
//        user.setName("John Doe");
//
//        AppUser savedUser = userRepo.save(user);
//        AppUser existUser = entityManager.find(AppUser.class, savedUser.getId());
//        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
//    }
//
//    @Test
//    public void testAddRoleToNewUser() {
//        Role roleAdmin = roleRepo.findByName("Admin");
//        AppUser user = new AppUser();
//        user.setEmail("admin@gmail.com");
//        user.setPassword("admin");
//        user.setName("Nazar Zhuhan");
//        user.addRole(roleAdmin);
//
//        AppUser savedUser = userRepo.save(user);
//        assertThat(savedUser.getRoles().size()).isEqualTo(1);
//    }
//
//    @Test
//    public void testAddRoleToExistingUser() {
//        AppUser user = new AppUser();
//        user.setEmail("test2@gmail.com");
//        user.setPassword("test");
//        user.setName("John Doe");
//
//        Role userRole = new Role("User");
//        Role adminRole = new Role("Admin");
//        Role customerRole = new Role("Customer");
//
//        roleRepo.saveAll(List.of(userRole, adminRole, customerRole));
//
//        userRepo.save(user);
//
//        AppUser userIn = userRepo.findByEmail("test2@gmail.com").get();
//        Role roleUser = roleRepo.findByName("User");
//        Role roleCustomer = roleRepo.findByName("Customer");
//
//        userIn.addRole(roleUser);
//        userIn.addRole(roleCustomer);
//        AppUser savedUser = userRepo.save(userIn);
//
//        assertThat(savedUser.getRoles().size()).isEqualTo(2);
//    }
//}
