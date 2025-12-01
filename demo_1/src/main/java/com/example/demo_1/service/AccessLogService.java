package com.example.demo_1.service;

import com.example.demo_1.dto.AccessLogDto;
import com.example.demo_1.exception.ResourceNotFoundException;
import com.example.demo_1.model.AccessLog;
import com.example.demo_1.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessLogService {

    private AccessLogRepository accessLogRepository;


    @Transactional(readOnly = true)
    public List<AccessLogDto> getAllAccessLogs() {
        return accessLogRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccessLogDto getAccessLogById(Long id) {
        AccessLog accessLog = accessLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccessLog", "id", id));
        return mapToDto(accessLog);
    }

    @Transactional(readOnly = true)
    public List<AccessLogDto> getAccessLogsByDateRange(Date startDate, Date endDate) {
        return accessLogRepository.findByEntryTimeBetween(startDate, endDate).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AccessLogDto mapToDto(AccessLog accessLog) {
        AccessLogDto dto = new AccessLogDto();
        dto.setId(accessLog.getId());
        dto.setPurpose(accessLog.getRemarks());

        // Note: The current AccessLog model uses AccessCard instead of direct Visitor/SecurityGuard
        // This mapper provides a basic structure; adjust based on actual relationships
        
        return dto;
    }
}
