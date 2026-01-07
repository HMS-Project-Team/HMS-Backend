package com.example.hms.HMS.controllers;

import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.RoomTypeRequestDto;
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.exceptionHandlers.InvalidPageSizeException;
import com.example.hms.HMS.exceptionHandlers.ResourceNotFoundException;
import com.example.hms.HMS.services.RoomTypeService;

import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import com.example.hms.HMS.enums.PrivilegeType;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.ROOMTYPE)
@RequiredArgsConstructor
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @PostMapping(EndpointBundle.ADD)
    @RequirePrivilege(privilege = "/rooms/types", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<RoomTypeResponseDto>> createRoomType(
            @Valid @RequestBody RoomTypeRequestDto roomTypeRequestDto) {
        RoomTypeResponseDto newRoomType = roomTypeService.createRoomType(roomTypeRequestDto);

        if (newRoomType != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.CREATED.getCode(),
                    ValidationMessages.SAVED_SUCCESSFULLY,
                    newRoomType));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                    ValidationMessages.BAD_REQUEST,
                    null));
        }
    }

    @GetMapping(EndpointBundle.ID)
    @RequirePrivilege(privilege = "/rooms/types", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<RoomTypeResponseDto>> getRoomTypeById(@PathVariable Long id) {
        RoomTypeResponseDto data = roomTypeService.getRoomTypeById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiResponseStatusCodes.OK.getCode(),
                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                data));
    }

    @PutMapping(EndpointBundle.ID)
    @RequirePrivilege(privilege = "/rooms/types", type = PrivilegeType.WRITE_ACCESS)
    public ResponseEntity<ResponseWrapper<RoomTypeResponseDto>> updateRoomType(
            @PathVariable Long id,
            @Valid @RequestBody RoomTypeRequestDto roomTypeRequestDto) {

        RoomTypeResponseDto updatedRoomType = roomTypeService.updateRoomType(id, roomTypeRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.UPDATED_SUCCESSFULLY,
                        updatedRoomType));
    }

    @DeleteMapping(EndpointBundle.ID)
    @RequirePrivilege(privilege = "/rooms/types", type = PrivilegeType.MAINTAIN_ACCESS)
    public ResponseEntity<ResponseWrapper<String>> deleteRoomType(@Valid @PathVariable Long id) {
        boolean deleted = roomTypeService.deleteRoomType(id);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.OK.getCode(),
                    ValidationMessages.DELETED_SUCCESSFULLY,
                    null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
                    RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                    ValidationMessages.DELETE_FAILED,
                    null));
        }
    }

    @GetMapping
    @RequirePrivilege(privilege = "/rooms/types", type = PrivilegeType.READ_ACCESS)
    public ResponseEntity<ResponseWrapper<Page<RoomTypeResponseDto>>> getAllroomType(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0) {
            throw new InvalidPageSizeException(ValidationMessages.INVALID_PAGE_SIZE_MSG);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<RoomTypeResponseDto> roomtypes = roomTypeService.getAllRoomtypes(pageable);

        if (roomtypes.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NOT_FOUND);
        }

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.OK.getCode(),
                        ValidationMessages.RETRIEVED_SUCCESSFULLY,
                        roomtypes));
    }
}
