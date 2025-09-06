package com.moyeorak.content_service.service;

import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.content_service.common.AdminAuthHelper;
import com.moyeorak.content_service.dto.FacilityCreateRequest;
import com.moyeorak.content_service.dto.FacilityResponse;
import com.moyeorak.content_service.entity.Facility;
import com.moyeorak.content_service.entity.Region;
import com.moyeorak.content_service.repository.FacilityRepository;
import com.moyeorak.content_service.repository.RegionRepository;
import jakarta.transaction.Transactional;
import com.moyeorak.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FacilityServiceImpl implements FacilityService {

    private final RegionRepository regionRepository;
    private final FacilityRepository facilityRepository;
    private final AdminAuthHelper adminAuthHelper;

    @Override
    @Transactional
    public FacilityResponse createFacility(FacilityCreateRequest request, Long userId, String role) {
        adminAuthHelper.validateAdmin(role);

        Long regionId = request.getRegionId();
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));

        if (facilityRepository.existsByNameAndRegionId(request.getName(), regionId)) {
            throw new BusinessException(ErrorCode.FACILITY_NAME_DUPLICATE);
        }

        Facility facility = Facility.builder()
                .name(request.getName())
                .location(request.getLocation())
                .address(request.getAddress())
                .contact(request.getContact())
                .imageUrl(request.getImageUrl())
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .area(request.getArea())
                .usageStartTime(request.getUsageStartTime())
                .usageEndTime(request.getUsageEndTime())
                .region(region)
                .build();

        Facility saved = facilityRepository.save(facility);

        return FacilityResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .location(saved.getLocation())
                .address(saved.getAddress())
                .contact(saved.getContact())
                .imageUrl(saved.getImageUrl())
                .capacity(saved.getCapacity())
                .description(saved.getDescription())
                .area(saved.getArea())
                .usageStartTime(saved.getUsageStartTime())
                .usageEndTime(saved.getUsageEndTime())
                .regionId(region.getId())
                .build();
    }
}
