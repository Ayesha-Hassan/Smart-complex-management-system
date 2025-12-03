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
            System.out.println("hey1");
            // Generate JWT token
            java.util.Map<String, Object> claims = new java.util.HashMap<>();
            claims.put("role", java.util.List.of("ROLE_" + user.getRole()));
            claims.put("id", user.getId().toString());
            claims.put("name", user.getName());
            
            String token = jwtTokenProvider.generateTokenWithClaims(
                    user.getEmail(),
                    claims
            );
            System.out.println("hey2");
            return ResponseEntity.ok(new LoginResponse(
                    token,
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            ));
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Error during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new com.example.demo_1.dto.ErrorResponse("Invalid email or password: " + e.getMessage()));
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
