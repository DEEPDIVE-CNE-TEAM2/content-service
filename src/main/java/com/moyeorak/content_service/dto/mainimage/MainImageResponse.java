package com.moyeorak.content_service.dto.mainimage;

import com.moyeorak.content_service.entity.MainImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MainImageResponse {
    private Long id;
    private String imageUrl;
    private Integer displayOrder;
    private boolean isActive;

    public static MainImageResponse from(MainImage image) {
        return MainImageResponse.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .displayOrder(image.getDisplayOrder())
                .isActive(image.isActive())
                .build();
    }
}