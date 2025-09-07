package com.moyeorak.content_service.service;

import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.common.exception.ErrorCode;
import com.moyeorak.content_service.common.AdminAuthHelper;
import com.moyeorak.content_service.dto.mainimage.MainImageCreateRequest;
import com.moyeorak.content_service.dto.mainimage.MainImageResponse;
import com.moyeorak.content_service.dto.mainimage.MainImageUpdateRequest;
import com.moyeorak.content_service.entity.MainImage;
import com.moyeorak.content_service.entity.Region;
import com.moyeorak.content_service.repository.MainImageRepository;
import com.moyeorak.content_service.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainImageServiceImpl implements MainImageService {

    private final MainImageRepository mainImageRepository;
    private final RegionRepository regionRepository;
    private final AdminAuthHelper adminAuthHelper;

    @Override
    @Transactional(readOnly = true)
    public List<MainImageResponse> getMainImages(String role, Long regionId) {
        adminAuthHelper.validateAdmin(role);

        List<MainImage> images = mainImageRepository.findByRegionIdOrderByDisplayOrderAsc(regionId);
        return images.stream()
                .map(MainImageResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public MainImageResponse createMainImage(MainImageCreateRequest dto, String role, Long regionId) {
        adminAuthHelper.validateAdmin(role);

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));

        Integer maxOrder = mainImageRepository.findMaxDisplayOrderByRegionId(regionId);
        int nextOrder = (maxOrder != null) ? maxOrder + 1 : 1;

        MainImage image = MainImage.builder()
                .title("")
                .imageUrl(dto.getImageUrl())
                .displayOrder(nextOrder)
                .isActive(true)
                .region(region)
                .build();
        return MainImageResponse.from(mainImageRepository.save(image));
    }

    @Override
    @Transactional
    public void updateMainImages(List<MainImageUpdateRequest> requestList, String role) {
        adminAuthHelper.validateAdmin(role);

        for (MainImageUpdateRequest req : requestList) {
            MainImage image = mainImageRepository.findById(req.getId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MAIN_IMAGE_ID));

            image.changeDisplayOrder(req.getDisplayOrder());
            image.changeActiveStatus(req.getIsActive());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id, String role) {
        adminAuthHelper.validateAdmin(role);

        if (!mainImageRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_MAIN_IMAGE_ID);
        }

        mainImageRepository.deleteById(id);
    }

    // 일반 사용자 조회
    @Transactional(readOnly = true)
    public List<MainImageResponse> getByRegion(Long targetRegionId) {
        return mainImageRepository.findByRegionIdAndIsActiveTrueOrderByDisplayOrderAsc(targetRegionId).stream()
                .map(MainImageResponse::from)
                .toList();
    }
}
