package com.example.demo_1.repository;

import com.example.demo_1.model.MaintenanceRequest;
import com.example.demo_1.model.MaintenanceStatus;
import com.example.demo_1.model.MaintenancePriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    List<MaintenanceRequest> findByResidentId(Long residentId);
    List<MaintenanceRequest> findByStatus(MaintenanceStatus status);
    List<MaintenanceRequest> findByTechnicianId(Long technicianId);
    List<MaintenanceRequest> findByPriority(MaintenancePriority priority);
    List<MaintenanceRequest> findByRequestDateBetween(Date startDate, Date endDate);
    List<MaintenanceRequest> findByStatusAndPriority(MaintenanceStatus status, MaintenancePriority priority);
}
