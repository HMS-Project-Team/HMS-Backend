package com.example.hms.HMS.controllers;

import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.RoomAreaRequestDto;
import com.example.hms.HMS.dtos.responses.RoomAreaResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.RoomAreaService;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import com.example.hms.HMS.enums.PrivilegeType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointBundle.ROOMAREA)
@RequiredArgsConstructor
public class RoomAreaController {
        private final RoomAreaService roomAreaService;

        @PostMapping(EndpointBundle.CREATE_ROOMAREA)
        @RequirePrivilege(privilege = "/rooms/areas", type = PrivilegeType.WRITE_ACCESS)
        public ResponseEntity<ResponseWrapper<RoomAreaResponseDto>> createRoomArea(
                        @Valid @RequestBody RoomAreaRequestDto roomAreaRequestDto) {
                RoomAreaResponseDto createdRoomArea = roomAreaService.createRoomArea(roomAreaRequestDto);

                if (createdRoomArea != null) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        new ResponseWrapper<>(
                                                        RestApiResponseStatusCodes.OK.getCode(),
                                                        ValidationMessages.SAVED_SUCCESSFULLY,
                                                        createdRoomArea));
                } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                        new ResponseWrapper<>(
                                                        RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                                                        ValidationMessages.SAVE_FAILED,
                                                        null));
                }

        }

        @GetMapping(EndpointBundle.ID)
        @RequirePrivilege(privilege = "/rooms/areas", type = PrivilegeType.READ_ACCESS)
        public ResponseEntity<ResponseWrapper<RoomAreaResponseDto>> getRoomArea(@PathVariable Long id) {
                RoomAreaResponseDto roomArea = roomAreaService.getRoomArea(id);

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                                RestApiResponseStatusCodes.OK.getCode(), ValidationMessages.RETRIEVED_SUCCESSFULLY,
                                roomArea));
        }

        @GetMapping
        @RequirePrivilege(privilege = "/rooms/areas", type = PrivilegeType.READ_ACCESS)
        public ResponseEntity<ResponseWrapper<Page<RoomAreaResponseDto>>> getAllRoomArea(Pageable pageable) {
                Page<RoomAreaResponseDto> roomAreas = roomAreaService.getAllRoomArea(pageable);
                return ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseWrapper<>(
                                                RestApiResponseStatusCodes.OK.getCode(),
                                                ValidationMessages.RETRIEVED_SUCCESSFULLY,
                                                roomAreas));
        }

        @PutMapping(EndpointBundle.ID)
        @RequirePrivilege(privilege = "/rooms/areas", type = PrivilegeType.WRITE_ACCESS)
        public ResponseEntity<ResponseWrapper<RoomAreaResponseDto>> updateRoomArea(
                        @PathVariable Long id,
                        @Valid @RequestBody RoomAreaRequestDto roomAreaRequestDto) {

                RoomAreaResponseDto responseDto = roomAreaService.updateRoomArea(id, roomAreaRequestDto);

                return ResponseEntity.ok(
                                new ResponseWrapper<>(
                                                RestApiResponseStatusCodes.OK.getCode(),
                                                ValidationMessages.UPDATED_SUCCESSFULLY,
                                                responseDto));
        }

        @DeleteMapping(EndpointBundle.ID)
        @RequirePrivilege(privilege = "/rooms/areas", type = PrivilegeType.MAINTAIN_ACCESS)
        public ResponseEntity<ResponseWrapper<Boolean>> deleteRoomArea(@PathVariable Long id) {
                roomAreaService.deleteRoomArea(id);

                return ResponseEntity.status(HttpStatus.OK).body(
                                new ResponseWrapper<>(
                                                RestApiResponseStatusCodes.OK.getCode(),
                                                ValidationMessages.DELETED_SUCCESSFULLY, true));
        }
}
