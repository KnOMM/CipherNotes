package com.example.hw04.conf;

import com.example.hw04.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) ->
                                authorize
                                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/index")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/caesar")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/access-denied")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/users")).hasRole("ADMIN")
//                                .requestMatchers(new AntPathRequestMatcher("/test")).hasAnyRole("USER", "ADMIN")
                                        .requestMatchers(new AntPathRequestMatcher("/caesar/**")).hasAnyRole("USER", "ADMIN")
                                        .requestMatchers(new AntPathRequestMatcher("/vigenere/**")).hasAnyRole("USER", "ADMIN")
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .usernameParameter("username")
                                .defaultSuccessUrl("/test")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                ).exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler()));
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder2());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
