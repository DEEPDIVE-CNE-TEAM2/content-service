package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.RegionRequest;
import com.moyeorak.content_service.dto.RegionResponse;

import java.util.List;

public interface RegionService {
    RegionResponse createRegion(RegionRequest request);
    List<RegionResponse> getAllRegions();
    RegionResponse getRegion(Long id);
    RegionResponse updateRegion(Long id, RegionRequest request);
    void deleteRegion(Long id);
}