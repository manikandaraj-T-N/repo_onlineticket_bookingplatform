package com.example.onlinebooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authenticationProvider(authenticationProvider()) 
            .authorizeHttpRequests(auth -> auth
                
                .requestMatchers(
                    "/",
                    "/home",
                    "/login",
                    "/signup",
                    "/homeflight","/flights",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/hometrain", "/trains", 
                    "/homebus", "/buses",      
                    "/homesports", "/sports", 
                    "/homemovies", "/movies",
                    "/homeevents", "/events",
                    "/homesports","/sports",
                    "/homeactivities","/activities",
                    "/homehotels", "/hotels"
                    // "/booking/confirm"
                ).permitAll()

                // protected pages - login required
                .requestMatchers(
                    "/booking/**",
                    "/mybookings",
                    "/trainbooking/**",
                    "/busbooking/**",
                     "/moviebooking/**" ,
                    "/activitybooking/**",
                    "/sportbooking/**" ,
                     "/trainbooking/**",   
                     "/busbooking/**" ,
                     "/eventbooking/**",
                     "/hotelbooking/**"
                ).authenticated()

                // all other pages require login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .usernameParameter("email")
                .passwordParameter("password")        
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/signup",
        "/booking/confirm",
         "/trainbooking/confirm",
        "/busbooking/confirm",
        "/moviebooking/confirm",
        "/sportbooking/confirm",
        "/eventbooking/confirm",
        "/activitybooking/confirm",
        "/hotelbooking/confirm",

        "/booking/cancel/**",        
        "/busbooking/cancel/**",      
        "/trainbooking/cancel/**",    
        "/activitybooking/cancel/**", 
        "/sportbooking/cancel/**",    
        "/eventbooking/cancel/**",  
        "/moviebooking/cancel/**",    
        "/hotelbooking/cancel/**"   
        )  // allow form POST
            );

        return http.build();
    }
}