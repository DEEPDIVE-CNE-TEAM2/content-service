package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.mainimage.MainImageCreateRequest;
import com.moyeorak.content_service.dto.mainimage.MainImageResponse;
import com.moyeorak.content_service.dto.mainimage.MainImageUpdateRequest;

import java.util.List;

public interface MainImageService {

    List<MainImageResponse> getMainImages(String role, Long regionId);

    MainImageResponse createMainImage(MainImageCreateRequest dto, String role, Long regionId);

    void updateMainImages(List<MainImageUpdateRequest> requestList, String role);

    void deleteById(Long id, String role);
}