package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.RegionRequest;
import com.moyeorak.content_service.dto.RegionResponse;
import com.moyeorak.content_service.entity.Region;
import com.moyeorak.content_service.repository.RegionRepository;
import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    @Transactional
    public RegionResponse createRegion(RegionRequest request) {
        validateRegionNameUnique(request.getName());

        Region region = Region.builder()
                .name(request.getName())
                .build();

        Region saved = regionRepository.save(region);

        return toResponse(saved);
    }

    @Override
    public List<RegionResponse> getAllRegions() {
        return regionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public RegionResponse getRegion(Long id) {
        Region region = findRegionById(id);
        return toResponse(region);
    }

    @Override
    @Transactional
    public RegionResponse updateRegion(Long id, RegionRequest request) {
        Region region = findRegionById(id);

        if (!region.getName().equals(request.getName())) {
            validateRegionNameUnique(request.getName());
        }

        region.setName(request.getName());

        return toResponse(region);
    }

    @Override
    @Transactional
    public void deleteRegion(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_REGION);
        }

        regionRepository.deleteById(id);
    }



    private Region findRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));
    }

    private void validateRegionNameUnique(String name) {
        if (regionRepository.existsByName(name)) {
            throw new BusinessException(ErrorCode.REGION_NAME_DUPLICATE);
        }
    }

    private RegionResponse toResponse(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}