package com.moyeorak.content_service.dto.mainimage;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainImageCreateRequest {

    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imageUrl;

}
