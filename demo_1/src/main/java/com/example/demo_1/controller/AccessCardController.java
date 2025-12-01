package com.example.demo_1.controller;

import com.example.demo_1.dto.AccessCardDto;
import com.example.demo_1.model.CardStatus;
import com.example.demo_1.service.AccessCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access-cards")
@RequiredArgsConstructor
public class AccessCardController {

    private final AccessCardService accessCardService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<List<AccessCardDto>> getAllAccessCards() {
        List<AccessCardDto> accessCards = accessCardService.getAllAccessCards();
        return ResponseEntity.ok(accessCards);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD', 'RESIDENT')")
    public ResponseEntity<AccessCardDto> getAccessCardById(@PathVariable Long id) {
        AccessCardDto accessCard = accessCardService.getAccessCardById(id);
        return ResponseEntity.ok(accessCard);
    }

    @PostMapping("/issue/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<AccessCardDto> issueAccessCard(@PathVariable Long userId) {
        AccessCardDto accessCard = accessCardService.issueAccessCard(userId);
        return new ResponseEntity<>(accessCard, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<AccessCardDto> updateCardStatus(
            @PathVariable Long id,
            @RequestParam CardStatus status) {
        AccessCardDto accessCard = accessCardService.updateCardStatus(id, status);
        return ResponseEntity.ok(accessCard);
    }
}
