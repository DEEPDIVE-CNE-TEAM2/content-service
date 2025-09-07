package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.MessageResponse;
import com.moyeorak.content_service.dto.mainimage.MainImageCreateRequest;
import com.moyeorak.content_service.dto.mainimage.MainImageResponse;
import com.moyeorak.content_service.dto.mainimage.MainImageUpdateRequest;
import com.moyeorak.content_service.service.MainImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main-images")
public class MainImageController {

    private final MainImageService mainImageService;

    @Operation(summary = "관리자 메인이미지 전체 조회")
    @GetMapping
    public ResponseEntity<List<MainImageResponse>> getMainImages(
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId
    ) {
        log.info("메인이미지 리스트 요청 - role: {}, regionId: {}", role, regionId);
        return ResponseEntity.ok(mainImageService.getMainImages(role, regionId));
    }

    @Operation(summary = "관리자 메인이미지 생성")
    @PostMapping
    public ResponseEntity<MainImageResponse> createMainImage(
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @RequestBody MainImageCreateRequest request
    ) {
        log.info("메인이미지 생성 요청 - role: {}, regionId: {}", role, regionId);
        return ResponseEntity.ok(mainImageService.createMainImage(request, role, regionId));
    }

    @Operation(summary = "관리자 메인이미지 일괄 수정 (표시여부/순서)")
    @PatchMapping
    public ResponseEntity<MessageResponse> updateMainImages(
            @RequestHeader("X-User-Role") String role,
            @RequestBody List<MainImageUpdateRequest> requestList
    ) {
        log.info("메인이미지 일괄 수정 요청 - role: {}, 수정갯수: {}", role, requestList.size());
        mainImageService.updateMainImages(requestList, role);
        return ResponseEntity.ok(new MessageResponse("메인이미지가 수정되었습니다."));
    }

    @Operation(summary = "관리자 메인이미지 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteMainImage(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long id
    ) {
        log.info("메인이미지 삭제 요청 - role: {}, id: {}", role, id);
        mainImageService.deleteById(id, role);
        return ResponseEntity.ok(new MessageResponse("메인이미지가 삭제되었습니다."));
    }
}