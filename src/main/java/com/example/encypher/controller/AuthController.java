package com.example.encypher.controller;

import com.example.encypher.conf.JwtService;
import com.example.encypher.dto.UserDto;
import com.example.encypher.dto.UserDtoLogin;
import com.example.encypher.entity.AppUser;
import com.example.encypher.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    @Autowired
    AuthenticationManager authManager;
    private final JwtService jwtUtil;

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


    // handler method to handle login request
    @GetMapping("/login")
    public String loginGet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/index";
    }

//    @PostMapping("/login")
//    public String loginPost(@Valid @ModelAttribute("user") UserDtoLogin request, HttpServletResponse response) {
//        LOG.info("in login post");
//        try {
//            LOG.info("in login post try clause, before authenticate");
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            LOG.info("in login post try clause, after authenticate");
//
//
//            AppUser user = (AppUser) authentication.getPrincipal();
//            String accessToken = jwtUtil.generateAccessToken(user);
//            LOG.info("{}", accessToken);
//            response.setHeader("Bearer", accessToken);
//
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                    request.getUsername(), request.getPassword());
//            Authentication authentication2 = this.authManager.authenticate(token);
//// ...
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            context.setAuthentication(authentication2);
//            SecurityContextHolder.setContext(context);
//        } catch (BadCredentialsException exception) {
//            response.setStatus(403);
//        }
//        return "redirect:/index";
//    }

    @GetMapping("/access-denied")
    public String getAccessDenied(HttpServletResponse response) {

        response.setStatus(403);
        return "/error/accessDenied";
    }
}
