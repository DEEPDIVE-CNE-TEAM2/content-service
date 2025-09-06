package com.moyeorak.content_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegionRequest {

    @NotBlank(message = "지역명은 필수입니다.")
    @Pattern(regexp = "^[가-힣\\s]+구$", message = "지역명은 'oo구' 또는 'oo시 oo구' 형식이어야 합니다.")
    private String name;

}