package com.example.demo_1.repository;

import com.example.demo_1.model.Roles;
import com.example.demo_1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(Roles role);

    Optional<User> findByName(String name);

    void deleteByEmail(String email);
}
