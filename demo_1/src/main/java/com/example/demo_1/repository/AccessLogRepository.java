package com.example.demo_1.repository;

import com.example.demo_1.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
//    List<AccessLog> findByVisitorId(Long visitorId);
//    List<AccessLog> findBySecurityGuardId(Long securityGuardId);
    List<AccessLog> findByEntryTimeBetween(Date startDate, Date endDate);
}
