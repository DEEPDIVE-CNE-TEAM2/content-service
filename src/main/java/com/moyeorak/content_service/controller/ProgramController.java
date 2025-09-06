package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.facility.FacilityCreateRequest;
import com.moyeorak.content_service.dto.facility.FacilityCreateResponse;
import com.moyeorak.content_service.dto.program.ProgramCreateRequest;
import com.moyeorak.content_service.dto.program.ProgramResponse;
import com.moyeorak.content_service.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;
    @Operation(summary = "프로그램 등록")
    @PostMapping
    public ResponseEntity<ProgramResponse> createProgram(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @Valid @RequestBody ProgramCreateRequest request
    ) {
        log.info("프로그램 등록 요청 - title: {}, regionId: {}, role: {}", request.getTitle(), regionId, role);
        ProgramResponse response = programService.createProgram(request, userId, role, regionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
