package com.moyeorak.content_service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionResponse {
    private Long id;
    private String name;
}