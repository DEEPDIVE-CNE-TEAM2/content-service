package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.facility.FacilityCreateRequest;
import com.moyeorak.content_service.dto.facility.FacilityCreateResponse;
import com.moyeorak.content_service.dto.program.ProgramCreateRequest;
import com.moyeorak.content_service.dto.program.ProgramDetailResponse;
import com.moyeorak.content_service.dto.program.ProgramListResponse;
import com.moyeorak.content_service.dto.program.ProgramResponse;
import com.moyeorak.content_service.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "프로그램 리스트 조회")
    @GetMapping
    public ResponseEntity<List<ProgramListResponse>> getProgramsByRegionAndTitle(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @RequestParam(required = false) Long targetRegionId,
            @RequestParam(required = false) String title
    ) {
        Long finalRegionId = (targetRegionId != null) ? targetRegionId : regionId;

        List<ProgramListResponse> responses =
                programService.getProgramsByRegionAndTitle(role, finalRegionId, title);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "프로그램 상세 조회")
    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDetailResponse> getProgramDetail(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @PathVariable Long programId
    ) {
        ProgramDetailResponse response = programService.getProgramDetail(programId, role, regionId);
        return ResponseEntity.ok(response);
    }
}
