package com.example.demo_1.service;

import com.example.demo_1.dto.AccessCardDto;
import com.example.demo_1.exception.ResourceNotFoundException;
import com.example.demo_1.model.AccessCard;
import com.example.demo_1.model.CardStatus;
import com.example.demo_1.model.User;
import com.example.demo_1.repository.AccessCardRepository;
import com.example.demo_1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessCardService {

    private final AccessCardRepository accessCardRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AccessCardDto> getAllAccessCards() {
        return accessCardRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccessCardDto getAccessCardById(Long id) {
        AccessCard accessCard = accessCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccessCard", "id", id));
        return mapToDto(accessCard);
    }

    @Transactional
    public AccessCardDto issueAccessCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        AccessCard accessCard = new AccessCard();
        accessCard.setUser(user);
        accessCard.setCard_number(generateCardNumber());
        accessCard.setIssue_date(new Date());
        
        // Set expiry date to 1 year from now
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1);
        accessCard.setExpiry_date(calendar.getTime());
        
        accessCard.setStatus(CardStatus.Active);

        AccessCard savedCard = accessCardRepository.save(accessCard);
        return mapToDto(savedCard);
    }

    @Transactional
    public AccessCardDto updateCardStatus(Long id, CardStatus status) {
        AccessCard accessCard = accessCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AccessCard", "id", id));
        
        accessCard.setStatus(status);
        AccessCard updatedCard = accessCardRepository.save(accessCard);
        return mapToDto(updatedCard);
    }

    private Number generateCardNumber() {
        // Generate a random 8-digit card number
        Random random = new Random();
        return 10000000 + random.nextInt(90000000);
    }

    private AccessCardDto mapToDto(AccessCard accessCard) {
        AccessCardDto dto = new AccessCardDto();
        dto.setId(accessCard.getId());
        dto.setCardNumber(String.valueOf(accessCard.getCard_number()));
        dto.setIssueDate(accessCard.getIssue_date());
        dto.setExpiryDate(accessCard.getExpiry_date());
        dto.setStatus(accessCard.getStatus());

        if (accessCard.getUser() != null) {
            dto.setResidentId(accessCard.getUser().getId());
            dto.setResidentName(accessCard.getUser().getName());
        }

        return dto;
    }
}
