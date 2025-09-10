package com.moyeorak.content_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresignedUrlResponse {
    private String uploadUrl;  // S3에 PUT할 presigned URL
    private String fileUrl;    // 실제로 접근할 수 있는 이미지 URL (DB 저장용)
}
