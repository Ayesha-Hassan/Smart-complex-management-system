package com.example.demo_1.repository;

import com.example.demo_1.model.AccessCard;
import com.example.demo_1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessCardRepository extends JpaRepository<AccessCard, Long> {
    public Long countByUser(User user);
}
