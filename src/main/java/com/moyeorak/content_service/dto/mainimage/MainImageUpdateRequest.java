package com.moyeorak.content_service.dto.mainimage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainImageUpdateRequest {
    private Long id;
    private Integer displayOrder;
    private Boolean isActive;
}
