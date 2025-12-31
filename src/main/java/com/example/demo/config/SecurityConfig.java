package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers(
                            "/",
                            "/**/*.html",
                            "/**/*.js",
                            "/**/*.css",
                            "/**/*.png",
                            "/**/*.jpg",
                            "/**/*.ico"
                        ).permitAll()
            		.requestMatchers(
            		        "/login.html",
            		        "/products.html",
            		        "/orders.html",
            		        "/admin-products.html",
            		        "/admin-orders.html",
            		        "/admin.html",
            		        "/register.html",
            		        "/",
            		        "/index.html",
            		        "/css/**",
            		        "/js/**",
            		        "/error",
            		        "/admin-products.js"
            		    ).permitAll()
                .requestMatchers("/api/auth/**", "/error").permitAll()
                .requestMatchers("api/admin/**","/error").hasRole("ADMIN")
                .requestMatchers("/api/orders/admin/**").hasRole("ADMIN")
                .requestMatchers("api/orders/**","/errors").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
