package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.requests.GuestsRequestDto;
import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import com.example.hms.HMS.entities.Guests;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.GuestsMapper;
import com.example.hms.HMS.repositories.GuestsRepository;
import com.example.hms.HMS.specifications.GuestsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestsServiceImpl implements GuestsService {

    private final GuestsRepository guestsRepository;
    private final GuestsMapper guestsMapper;
    private final FileStorageService fileStorageService;

    @Override
    public GuestsResponseDto createGuest(GuestsRequestDto guestsRequestDto, MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("image is required");
        }

        // Store file locally
        String fileName = fileStorageService.storeFile(image);

        Guests guests = guestsMapper.toEntity(guestsRequestDto);
        guests.setIdentityImage(fileName);

        Guests saved = guestsRepository.save(guests);
        return mapToResponseDto(saved);
    }

    @Override
    public GuestsResponseDto getGuestsById(long id) {
        Guests guest = guestsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));

        return mapToResponseDto(guest);
    }

    @Override
    public boolean deleteGuest(Long id) {
        Guests guests = guestsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found Any Data for this id : " + id));

        // Delete the image file from local storage
        if (guests.getIdentityImage() != null && !guests.getIdentityImage().isEmpty()) {
            try {
                fileStorageService.deleteFile(guests.getIdentityImage());
            } catch (IOException e) {
                // Log the error or handle it as needed. For now, we continue deletion.
                System.err.println("Failed to delete image: " + e.getMessage());
            }
        }

        guestsRepository.deleteById(id);
        return true;
    }

    @Override
    public List<GuestsResponseDto> searchGuests(
            String query,
            String name,
            String email,
            String phone) {
        List<Guests> guests = guestsRepository.findAll(
                GuestsSpecification.search(query, name, email, phone));

        if (guests.isEmpty()) {
            throw new ResourceNotFoundException("No guests found matching the search criteria.");
        }

        return guests.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public Page<GuestsResponseDto> getAllGuests(int page, int size) {
        Page<Guests> guestsPage = guestsRepository.findAll(PageRequest.of(page, size));
        return guestsPage.map(this::mapToResponseDto);
    }

    @Override
    public GuestsResponseDto updateGuest(Long id, GuestsRequestDto guestsRequestDto, MultipartFile image)
            throws IOException {
        Guests existingGuest = guestsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));

        // Update personal details
        existingGuest.setFirstName(guestsRequestDto.getFirstName());
        existingGuest.setLastName(guestsRequestDto.getLastName());
        existingGuest.setIdentityNumber(guestsRequestDto.getIdentityNumber());
        existingGuest.setEmail(guestsRequestDto.getEmail());
        existingGuest.setPhoneNumber(guestsRequestDto.getPhoneNumber());
        existingGuest.setNationality(guestsRequestDto.getNationality());
        existingGuest.setCountry(guestsRequestDto.getCountry());
        existingGuest.setCity(guestsRequestDto.getCity());
        existingGuest.setAddressLine1(guestsRequestDto.getAddressLine1());
        existingGuest.setAddressLine2(guestsRequestDto.getAddressLine2());
        existingGuest.setDateOfBirth(guestsRequestDto.getDateOfBirth());

        // Handle image update
        if (image != null && !image.isEmpty()) {
            // Delete old image
            if (existingGuest.getIdentityImage() != null && !existingGuest.getIdentityImage().isEmpty()) {
                fileStorageService.deleteFile(existingGuest.getIdentityImage());
            }
            // Store new image
            String fileName = fileStorageService.storeFile(image);
            existingGuest.setIdentityImage(fileName);
        }

        Guests updated = guestsRepository.save(existingGuest);
        return mapToResponseDto(updated);
    }

    private GuestsResponseDto mapToResponseDto(Guests guests) {
        GuestsResponseDto dto = guestsMapper.toGuestsResponseDto(guests);
        if (guests.getIdentityImage() != null && !guests.getIdentityImage().isEmpty()) {
            dto.setIdentityImage(fileStorageService.getFileUrl(guests.getIdentityImage()));
        }
        return dto;
    }
}
