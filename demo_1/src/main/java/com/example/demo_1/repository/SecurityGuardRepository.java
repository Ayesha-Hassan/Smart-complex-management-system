package com.example.demo_1.repository;

import com.example.demo_1.model.SecurityGuard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityGuardRepository extends JpaRepository<SecurityGuard, Long> {

    /**
     * Custom query to find all security guards whose shift starts at a specific time,
     * ordered by name (ascending).
     */
    @Query("SELECT sg FROM SecurityGuard sg WHERE sg.shift_start = :shiftStartTime ORDER BY sg.name ASC")
    List<SecurityGuard> findGuardsByShiftStart(@Param("shiftStartTime") Time shiftStartTime);

    /**
     * Custom query to find all security guards whose shift ends at a specific time,
     * ordered by name (ascending).
     */
    @Query("SELECT sg FROM SecurityGuard sg WHERE sg.shift_end = :shiftEndTime ORDER BY sg.name ASC")
    List<SecurityGuard> findGuardsByShiftEnd(@Param("shiftEndTime") Time shiftEndTime);

    /**
     * Custom query to find all security guards ordered by name (ascending).
     */
    @Query("SELECT sg FROM SecurityGuard sg ORDER BY sg.name ASC")
    List<SecurityGuard> findAllOrderedByName();

    /**
     * Custom query to find a security guard by name.
     */
    @Query("SELECT sg FROM SecurityGuard sg WHERE sg.name = :name")
    Optional<SecurityGuard> findGuardByName(@Param("name") String name);

    /**
     * Custom query to find all security guards with a shift starting between two times.
     */
    @Query("SELECT sg FROM SecurityGuard sg WHERE sg.shift_start >= :startTime AND sg.shift_start <= :endTime ORDER BY sg.name ASC")
    List<SecurityGuard> findGuardsByShiftStartBetween(@Param("startTime") Time startTime, @Param("endTime") Time endTime);

    /**
     * Custom query to find all security guards with a shift ending between two times.
     */
    @Query("SELECT sg FROM SecurityGuard sg WHERE sg.shift_end >= :startTime AND sg.shift_end <= :endTime ORDER BY sg.name ASC")
    List<SecurityGuard> findGuardsByShiftEndBetween(@Param("startTime") Time startTime, @Param("endTime") Time endTime);
}
