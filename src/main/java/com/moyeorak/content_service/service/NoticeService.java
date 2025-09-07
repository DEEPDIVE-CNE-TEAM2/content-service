package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.notice.*;

import java.util.List;

public interface NoticeService {
    NoticeResponse createNotice(NoticeRequest dto, Long userId, String role, Long regionId);
    NoticeResponse getNoticeDetail(Long noticeId, String role);
    List<NoticeListResponse> getNoticeList(String role, Long regionId);
    NoticeResponse updateNotice(Long noticeId, NoticeRequest dto, String role);
    void deleteNotice(Long noticeId, String role);
    List<PublicNoticeListResponse> getPublicNotices(Long targetRegionId);
    PublicNoticeDetailResponse getPublicNoticeDetail(Long noticeId);
}
