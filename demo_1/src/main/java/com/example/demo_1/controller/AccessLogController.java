package com.example.demo_1.controller;

import com.example.demo_1.dto.AccessLogDto;
import com.example.demo_1.service.AccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
@RequiredArgsConstructor
public class AccessLogController {

    private AccessLogService accessLogService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<List<AccessLogDto>> getAllAccessLogs() {
        List<AccessLogDto> accessLogs = accessLogService.getAllAccessLogs();
        return ResponseEntity.ok(accessLogs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<AccessLogDto> getAccessLogById(@PathVariable Long id) {
        AccessLogDto accessLog = accessLogService.getAccessLogById(id);
        return ResponseEntity.ok(accessLog);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<List<AccessLogDto>> getAccessLogsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<AccessLogDto> accessLogs = accessLogService.getAccessLogsByDateRange(startDate, endDate);
        return ResponseEntity.ok(accessLogs);
    }
}
