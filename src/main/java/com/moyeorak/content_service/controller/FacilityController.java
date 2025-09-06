package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.facility.*;
import com.moyeorak.content_service.service.FacilityService;
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
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @Operation(summary = "시설 등록")
    @PostMapping
    public ResponseEntity<FacilityCreateResponse> createFacility(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @RequestBody @Valid FacilityCreateRequest request
    ) {
        log.info("시설 등록 요청 - name: {}, regionId: {}, userId: {}, role: {}",
                request.getName(), regionId, userId, role);
        FacilityCreateResponse response = facilityService.createFacility(request, userId, role, regionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "시설 리스트 조회")
    @GetMapping
    public ResponseEntity<List<AdminFacilityListResponse>> getFacilityList(
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId
    ) {
        log.info("관리자 시설 리스트 조회 요청 - regionId: {}, role: {}", regionId, role);
        List<AdminFacilityListResponse> facilities = facilityService.getFacilityList(regionId, role);
        return ResponseEntity.ok(facilities);
    }

    @Operation(summary = "시설 상세 조회")
    @GetMapping("/{facilityId}")
    public ResponseEntity<AdminFacilityDetailResponse> getFacilityDetail(
            @PathVariable Long facilityId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId
    ) {
        log.info("관리자 시설 상세 조회 요청 - facilityId: {}, regionId: {}, role: {}",
                facilityId, regionId, role);
        AdminFacilityDetailResponse detail = facilityService.getFacilityDetail(facilityId, regionId, role);
        return ResponseEntity.ok(detail);
    }

    @Operation(summary = "시설 리스트 조회 (일반 사용자)")
    @GetMapping("/public")
    public ResponseEntity<List<FacilityDetailResponse>> getFacilityListForUser(
            @RequestParam("regionId") Long regionId
    ) {
        log.info("일반 사용자 시설 리스트 조회 요청 - regionId: {}", regionId);
        List<FacilityDetailResponse> response = facilityService.getFacilityListForUser(regionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시설 수정")
    @PutMapping("/{facilityId}")
    public ResponseEntity<AdminFacilityDetailResponse> updateFacility(
            @PathVariable Long facilityId,
            @Valid @RequestBody FacilityUpdateRequest request,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId
    ) {
        log.info("시설 수정 요청 - facilityId: {}, role: {}, regionId: {}", facilityId, role, regionId);
        AdminFacilityDetailResponse response = facilityService.updateFacility(facilityId, request, role, regionId);
        log.info("시설 수정 완료 - facilityId: {}", facilityId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시설 삭제")
    @DeleteMapping("/{facilityId}")
    public ResponseEntity<Void> deleteFacility(
            @PathVariable Long facilityId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId
    ) {
        log.info("시설 삭제 요청 - facilityId: {}, role: {}, regionId: {}", facilityId, role, regionId);
        facilityService.deleteFacility(facilityId, role, regionId);
        log.info("시설 삭제 완료 - facilityId: {}", facilityId);
        return ResponseEntity.noContent().build();
    }
}