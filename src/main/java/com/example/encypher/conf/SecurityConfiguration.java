package com.example.encypher.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable
////                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
//                )
//                .authorizeHttpRequests(auth ->
//                        auth
//                                .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/index")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/access-denied")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/users")).hasRole("ADMIN")
//                                .requestMatchers(new AntPathRequestMatcher("/test")).hasRole("USER")
//                                .anyRequest()
//                                .authenticated())
//                .exceptionHandling(exception -> exception
//                        .accessDeniedHandler(accessDeniedHandler()))
//                .formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/test")
//                                .permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll()
//                ).sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return new CustomAccessDeniedHandler();
//    }
}
