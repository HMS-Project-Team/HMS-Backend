package com.example.hms.HMS.services;

import com.example.hms.HMS.entities.RoomArea;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.repositories.RoomAreaRepository;
import com.example.hms.HMS.utils.ValidationMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomAreaServiceImpl implements RoomAreaService {

    private final RoomAreaRepository roomAreaRepository;

    @Override
    public void deleteRoomArea(Long id) {
        RoomArea roomArea = roomAreaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.NOT_FOUND));

        roomAreaRepository.delete(roomArea);
    }
}
