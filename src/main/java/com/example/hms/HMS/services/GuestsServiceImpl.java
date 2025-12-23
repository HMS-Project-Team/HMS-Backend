package com.example.hms.HMS.services;

import com.example.hms.HMS.dtos.responses.GuestsResponseDto;
import com.example.hms.HMS.entities.Guests;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.mappers.GuestsMapper;
import com.example.hms.HMS.repositories.GuestsRepository;
import com.example.hms.HMS.specifications.GuestsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestsServiceImpl implements GuestsService {

    private final GuestsRepository guestsRepository;
    private final GuestsMapper guestsMapper;

    @Override
    public GuestsResponseDto getGuestsById(long id) {
        Guests guest = guestsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("\"Guest not found with id: " + id));

        return guestsMapper.toEntity(guest);
    }

    @Override
    public boolean deleteManageGuests(Long id) {
        Guests guests = guestsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found Any Data for this id : " + id));
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

        return guestsMapper.toDtoList(guests);
    }

}
