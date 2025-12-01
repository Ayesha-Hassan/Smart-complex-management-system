package com.example.demo_1.controller;

import com.example.demo_1.dto.LoginRequest;
import com.example.demo_1.dto.LoginResponse;
import com.example.demo_1.model.User;
import com.example.demo_1.security.JwtTokenProvider;
import com.example.demo_1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    /**
     * Login endpoint - authenticates user and returns JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Find user by email
            User user = userService.getUserByEmailEntity(loginRequest.getEmail());
            System.out.println("email: ");
            // Validate password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new com.example.demo_1.dto.ErrorResponse("Invalid credentials"));
            }
            System.out.println("hey");
            // Generate JWT token
            String token = jwtTokenProvider.generateTokenWithClaims(
                    user.getEmail(),
                    java.util.Map.of(
                            "role", java.util.List.of("ROLE_" + user.getRole()),
                            "id", user.getId(),
                            "name", user.getName()
                    )
            );
            System.out.println("hey");
            return ResponseEntity.ok(new LoginResponse(
                    token,
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new com.example.demo_1.dto.ErrorResponse("Invalid email or password"));
        }
    }

    /**
     * Validate JWT token endpoint
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new com.example.demo_1.dto.ErrorResponse("Invalid authorization header"));
            }

            String token = authHeader.substring(7);
            boolean isValid = jwtTokenProvider.validateToken(token);

            if (isValid) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                return ResponseEntity.ok(new com.example.demo_1.dto.ErrorResponse("Token is valid for user: " + username));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new com.example.demo_1.dto.ErrorResponse("Token is invalid or expired"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new com.example.demo_1.dto.ErrorResponse("Token validation failed: " + e.getMessage()));
        }
    }

    /**
     * Logout endpoint (client-side implementation)
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new com.example.demo_1.dto.ErrorResponse("Logged out successfully"));
    }
}
