package com.moyeorak.content_service.service;


import com.moyeorak.content_service.dto.facility.*;

import java.util.List;

public interface FacilityService {

    // 시설 등록
    FacilityCreateResponse createFacility(FacilityCreateRequest request, Long userId, String role, Long regionId);

    //시설 목록 조회
    AdminFacilityDetailResponse getFacilityDetail(Long facilityId, Long regionId, String role);

    List<AdminFacilityListResponse> getFacilityList(Long regionId, String role);

    List<FacilityDetailResponse> getFacilityListForUser(Long regionId);

    AdminFacilityDetailResponse updateFacility(Long facilityId, FacilityUpdateRequest request, String role, Long regionId);

    void deleteFacility(Long facilityId, String role, Long regionId);
}
