package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.RegionRequest;
import com.moyeorak.content_service.dto.RegionResponse;
import com.moyeorak.content_service.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "지역 생성")
    @PostMapping
    public ResponseEntity<RegionResponse> createRegion(@Valid @RequestBody RegionRequest request) {
        log.info("지역 생성 요청 진입 - name: {}", request.getName());
        RegionResponse created = regionService.createRegion(request);
        log.info("지역 생성 완료 - id: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "전체 지역 목록 조회")
    @GetMapping
    public ResponseEntity<List<RegionResponse>> getAllRegions() {
        log.info("지역 목록 조회 진입");
        List<RegionResponse> regions = regionService.getAllRegions();
        log.info("지역 목록 조회 완료 - 총 {}개", regions.size());
        return ResponseEntity.ok(regions);
    }

    @Operation(summary = "특정 지역 조회")
    @GetMapping("/{id}")
    public ResponseEntity<RegionResponse> getRegionById(@PathVariable Long id) {
        log.info("특정 지역 조회 진입 - ID: {}", id);
        RegionResponse region = regionService.getRegion(id);
        log.info("특정 지역 조회 완료 - name: {}", region.getName());
        return ResponseEntity.ok(region);
    }

    @Operation(summary = "지역 정보 수정")
    @PutMapping("/{id}")
    public ResponseEntity<RegionResponse> updateRegion(
            @PathVariable Long id,
            @Valid @RequestBody RegionRequest request
    ) {
        log.info("지역 수정 요청 진입 - ID: {}, 요청 값: {}", id, request);
        RegionResponse updated = regionService.updateRegion(id, request);
        log.info("지역 수정 완료 - ID: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "지역 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.info("지역 삭제 요청 진입 - ID: {}", id);
        regionService.deleteRegion(id);
        log.info("지역 삭제 완료 - ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}