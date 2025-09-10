package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.PresignedUrlRequest;
import com.moyeorak.content_service.dto.PresignedUrlResponse;
import com.moyeorak.content_service.service.PresignedUrlService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class PresignedUrlController {

    private final PresignedUrlService presignedUrlService;

    @Operation(summary = "S3 이미지 업로드용 Presigned URL 생성")
    @PostMapping("/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(@RequestBody PresignedUrlRequest request) {
        String fileName = request.getFileName();
        String contentType = request.getContentType();

        var result = presignedUrlService.generatePresignedUrl(fileName, contentType);

        PresignedUrlResponse response = new PresignedUrlResponse(
                result.get("uploadUrl"),
                result.get("fileUrl")
        );

        return ResponseEntity.ok(response);
    }
}