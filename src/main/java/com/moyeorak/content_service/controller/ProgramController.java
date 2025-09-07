package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.MessageResponse;
import com.moyeorak.content_service.dto.facility.FacilityCreateRequest;
import com.moyeorak.content_service.dto.facility.FacilityCreateResponse;
import com.moyeorak.content_service.dto.program.*;
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
        log.info("프로그램 리스트 조회 요청 - regionId: {}, userId: {}, role: {}, title: {}",
                finalRegionId, userId, role, title);
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
        log.info("프로그램 상세 조회 요청 - programId: {}, userId: {}, role: {}, regionId: {}",
                programId, userId, role, regionId);
        ProgramDetailResponse response = programService.getProgramDetail(programId, role, regionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "관리자 - 프로그램 수정")
    @PatchMapping("/{programId}")
    public ResponseEntity<Long> updateProgram(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @PathVariable Long programId,
            @Valid @RequestBody ProgramUpdateRequest request
    ) {
        log.info("프로그램 수정 요청 - programId: {}, regionId: {}, userId: {}, role: {}",
                programId, regionId, userId, role);

        Long updatedId = programService.updateProgram(programId, request, role, regionId);
        return ResponseEntity.ok(updatedId);
    }

    @Operation(summary = "관리자 - 프로그램 삭제")
    @DeleteMapping("/{programId}")
    public ResponseEntity<MessageResponse> deleteProgram(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @PathVariable Long programId
    ) {
        log.info("프로그램 삭제 요청 - programId: {}, regionId: {}, userId: {}, role: {}",
                programId, regionId, userId, role);

        programService.deleteProgram(programId, role, regionId);
        return ResponseEntity.ok(new MessageResponse("프로그램이 삭제되었습니다."));
    }

    @Operation(summary = "일반 사용자 조회")
    @GetMapping("/public")
    public ResponseEntity<List<ProgramDisplayResponse>> getPrograms(
            @RequestHeader(value = "X-User-Region-Id", required = false) Long regionId,
            @RequestParam(value = "targetRegionId", required = false) Long targetRegionId
    ) {
        log.info("일반 사용자 프로그램 목록 조회 요청 - targetRegionId: {}, regionId(X-User-Region-Id): {}",
                targetRegionId, regionId);

        if (targetRegionId != null) {
            return ResponseEntity.ok(programService.getProgramsByRegion(targetRegionId, regionId));
        } else {
            return ResponseEntity.ok(programService.getAllPrograms(regionId));
        }
    }

    @Operation(summary = "일반 사용자 상세조회")
    @GetMapping("/public/{id}")
    public ResponseEntity<ProgramDisplayResponse> getProgramById(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Region-Id", required = false) Long regionId
    ) {
        log.info("일반 사용자 프로그램 상세조회 요청 - programId: {}, regionId(X-User-Region-Id): {}", id, regionId);

        return ResponseEntity.ok(programService.getProgramById(id, regionId));
    }
}
