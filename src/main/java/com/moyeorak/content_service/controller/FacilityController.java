package com.moyeorak.content_service.controller;


import com.moyeorak.content_service.dto.FacilityCreateRequest;
import com.moyeorak.content_service.dto.FacilityResponse;
import com.moyeorak.content_service.service.FacilityService;
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
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @Operation(summary = "시설 등록")
    @PostMapping
    public ResponseEntity<FacilityResponse> createFacility(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-Region-Id") Long regionId,
            @RequestBody @Valid FacilityCreateRequest request
    ) {
        log.info("시설 등록 요청: {}, regionId: {}", request.getName(), regionId);
        FacilityResponse response = facilityService.createFacility(request, userId, role, regionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}