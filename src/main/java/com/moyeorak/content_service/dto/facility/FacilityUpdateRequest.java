package com.moyeorak.content_service.dto.facility;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityUpdateRequest {
    private String name;
    private String address;
    private String usageStartTime;
    private String usageEndTime;
    private String contact;
    private Integer capacity;
    private String description;
    private String imageUrl;
}