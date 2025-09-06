package com.moyeorak.content_service.dto.facility;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class FacilityResponse {

    private Long id;
    private String name;
    private String location;
    private String address;
    private String contact;
    private String imageUrl;
    private Integer capacity;
    private String description;
    private Integer area;
    private LocalTime usageStartTime;
    private LocalTime usageEndTime;
    private Long regionId;
}