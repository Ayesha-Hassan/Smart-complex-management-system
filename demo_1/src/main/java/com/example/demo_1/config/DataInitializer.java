package com.example.demo_1.config;

import com.example.demo_1.model.Roles;
import com.example.demo_1.model.User;
import com.example.demo_1.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a default admin user if no admin exists.
     * This runs automatically when the application starts.
     */
    @PostConstruct
    public void initializeDefaultAdmin() {
        // Check if any admin user exists
        if (userRepository.findByRole(Roles.ADMIN).isEmpty()) {
            User admin = new User();
            admin.setName("Administrator");
            admin.setEmail("admin@complex.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Change this password!
            admin.setRole(Roles.ADMIN);
            
            userRepository.save(admin);
            
            System.out.println("=================================================");
            System.out.println("DEFAULT ADMIN USER CREATED:");
            System.out.println("Email: admin@complex.com");
            System.out.println("Password: admin123");
            System.out.println("⚠️ CHANGE THIS PASSWORD IMMEDIATELY!");
            System.out.println("=================================================");
        }
    }
}
