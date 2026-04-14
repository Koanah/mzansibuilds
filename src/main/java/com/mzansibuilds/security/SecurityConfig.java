package com.mzansibuilds.security;

import com.mzansibuilds.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This bean tells Spring HOW to load a user from the database during login.
    // When someone submits the login form, Spring Security calls this automatically
    // with the username they typed. We look it up in our database and return it.
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.mzansibuilds.entity.User user = userRepository.findByUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("No user found with username: " + username));

            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        };
    }

    // BCrypt is for password hashing.
    // It is intentionally slow to compute, which makes brute-force attacks impractical.
    // This bean is what UserService's PasswordEncoder dependency resolves to.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This is where we control access rules (which pages anyone can see)
    // and which pages require a logged-in user.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Vaadin handles its own CSRF protection internally,
                // so we disable Spring Security's CSRF to avoid conflicts
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // These paths are accessible without logging in
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/register"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/VAADIN/**"),
                                new AntPathRequestMatcher("/vaadinServlet/**"),
                                new AntPathRequestMatcher("/frontend/**"),
                                new AntPathRequestMatcher("/icons/**"),
                                new AntPathRequestMatcher("/images/**")
                        ).permitAll()
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )

                // Required for H2 console -it renders inside an iframe,
                // which Spring Security blocks by default
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                );

        return http.build();
    }
}