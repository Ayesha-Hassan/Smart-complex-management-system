package com.example.demo_1.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configure HTTP security with JWT authentication and role-based access control
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints (no authentication required)
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        
                        // User endpoints (authenticated users)
                        .requestMatchers(HttpMethod.GET, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        
                        // Building endpoints
                        .requestMatchers(HttpMethod.GET, "/buildings/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/buildings/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/buildings/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/buildings/**").hasRole("ADMIN")
                        
                        // Guard endpoints
                        .requestMatchers(HttpMethod.GET, "/guards/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/guards/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/guards/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/guards/**").hasRole("ADMIN")
                        
                        // Visitor endpoints
                        .requestMatchers(HttpMethod.GET, "/visitors/**").hasAnyRole("ADMIN", "RESIDENT", "GUARD")
                        .requestMatchers(HttpMethod.POST, "/visitors/register").hasAnyRole("GUARD", "RESIDENT")
                        .requestMatchers(HttpMethod.POST, "/visitors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/visitors/**").hasAnyRole("GUARD", "RESIDENT")
                        .requestMatchers(HttpMethod.DELETE, "/visitors/**").hasRole("ADMIN")
                        
                        // Entry pass endpoints
                        .requestMatchers(HttpMethod.GET, "/entry-passes/**").hasAnyRole("ADMIN", "GUARD", "RESIDENT")
                        .requestMatchers(HttpMethod.POST, "/entry-passes/**").hasAnyRole("ADMIN", "GUARD")
                        .requestMatchers(HttpMethod.DELETE, "/entry-passes/**").hasRole("ADMIN")
                        
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Password encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager bean
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * CORS configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
