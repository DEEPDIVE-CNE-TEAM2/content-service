package com.moyeorak.content_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PresignedUrlRequest {
    private String fileName;      // 예: abc123.png
    private String contentType;   // 예: image/png
}