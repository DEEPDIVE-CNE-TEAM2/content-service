package com.moyeorak.content_service.dto.facility;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityDetailResponse {
    private Long id;
    private String location;     // 시설 이름
    private String address;
    private String usageTime;    // 예: "09:00 ~ 18:00"
    private Integer capacity;
    private String imageUrl;
    private String description;
    private String contact;
}