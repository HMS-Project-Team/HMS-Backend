package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.EmailRequestDto;
import com.example.hms.HMS.dtos.responses.EmailResponseDto;
import com.example.hms.HMS.entities.Email;
import com.example.hms.HMS.entities.Hotel;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.EmailMapper;
import com.example.hms.HMS.repositories.EmailRepository;
import com.example.hms.HMS.repositories.HotelRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConfigServiceImpl implements EmailConfigService {

    private final EmailRepository emailRepository;
    private final HotelRepository hotelRepository;
    private final EmailMapper emailMapper;

    @Override
    public EmailResponseDto createEmailConfig(EmailRequestDto emailRequestDto) {
        // Validate email format
        validateEmailFormat(emailRequestDto.getSentEmail());

        // Validate CC email if provided
        if (emailRequestDto.getCcMailAddress() != null && !emailRequestDto.getCcMailAddress().trim().isEmpty()) {
            validateEmailFormat(emailRequestDto.getCcMailAddress());
        }

        // Check if hotel exists
        if (emailRequestDto.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(emailRequestDto.getHotelId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Hotel not found with ID: " + emailRequestDto.getHotelId()));

            // Check if email config already exists for this hotel
            if (emailRepository.existsByHotelId(emailRequestDto.getHotelId())) {
                throw new DataIntegrityViolationException("Email configuration already exists for this hotel");
            }
        }

        Email email = emailMapper.toEntity(emailRequestDto);

        // Set hotel if hotelId is provided
        if (emailRequestDto.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(emailRequestDto.getHotelId()).get();
            email.setHotel(hotel);
        }

        Email savedEmail = emailRepository.save(email);
        return emailMapper.toResponseDto(savedEmail);
    }

    @Override
    public EmailResponseDto updateEmailConfig(Long id, EmailRequestDto emailRequestDto) {
        Email existingEmail = emailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email configuration not found with ID: " + id));

        // Validate email format
        validateEmailFormat(emailRequestDto.getSentEmail());

        // Validate CC email if provided
        if (emailRequestDto.getCcMailAddress() != null && !emailRequestDto.getCcMailAddress().trim().isEmpty()) {
            validateEmailFormat(emailRequestDto.getCcMailAddress());
        }

        // Update fields
        existingEmail.setDisplayName(emailRequestDto.getDisplayName());
        existingEmail.setSentEmail(emailRequestDto.getSentEmail());
        existingEmail.setHostName(emailRequestDto.getHostName());
        existingEmail.setPort(emailRequestDto.getPort());
        existingEmail.setProtocol(emailRequestDto.getProtocol());
        existingEmail.setPassword(emailRequestDto.getPassword());
        existingEmail.setCcMailAddress(emailRequestDto.getCcMailAddress());

        Email updatedEmail = emailRepository.save(existingEmail);
        return emailMapper.toResponseDto(updatedEmail);
    }

    @Override
    public EmailResponseDto getEmailConfigById(Long id) {
        Email email = emailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email configuration not found with ID: " + id));
        return emailMapper.toResponseDto(email);
    }

    @Override
    public EmailResponseDto getEmailConfigByHotelId(Long hotelId) {
        Email email = emailRepository.findByHotelId(hotelId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Email configuration not found for hotel ID: " + hotelId));
        return emailMapper.toResponseDto(email);
    }

    @Override
    public boolean deleteEmailConfig(Long id) {
        if (!emailRepository.existsById(id)) {
            throw new ResourceNotFoundException("Email configuration not found with ID: " + id);
        }
        emailRepository.deleteById(id);
        return true;
    }

    private void validateEmailFormat(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(ValidationMessages.INVALID_EMAIL);
        }

        // Check if email contains @ and .
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException(ValidationMessages.INVALID_EMAIL);
        }

        // Validate email format using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException(ValidationMessages.INVALID_EMAIL);
        }
    }
}
