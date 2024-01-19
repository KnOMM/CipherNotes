package com.example.hw04.controller;

import com.example.hw04.dto.UserDto;
import com.example.hw04.entity.AppUser;
import com.example.hw04.service.CypherService;
import com.example.hw04.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("listUsers", users);
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {

            UserDto user = new UserDto();
            model.addAttribute("user", user);
            return "register";
        }
        return "redirect:/index";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model,
                               HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            Optional<AppUser> existingUser = userService.findUserByEmail(userDto.getEmail());

            if (existingUser.isPresent() && existingUser.get().getEmail() != null && !existingUser.get().getEmail().isEmpty()) {
                result.rejectValue("email", null,
                        "There is already an account registered with the same email");
            }

            if (result.hasErrors()) {
                model.addAttribute("user", userDto);
                return "/register";
            }

            String jwtToken = userService.saveUser(userDto).getToken();
            response.setHeader("JWT", jwtToken);
            return "redirect:/register?success";
        }

        return "redirect:/index";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

//    @GetMapping("/test")
//    public String test(Model model) {
////        model.addAttribute("user", user);
//        return "test";
//    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/index";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied(HttpServletResponse response) {

        response.setStatus(403);
        return "/error/accessDenied";
    }
}
