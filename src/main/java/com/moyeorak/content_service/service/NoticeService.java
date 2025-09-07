package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.notice.NoticeListResponse;
import com.moyeorak.content_service.dto.notice.NoticeRequest;
import com.moyeorak.content_service.dto.notice.NoticeResponse;

import java.util.List;

public interface NoticeService {
    NoticeResponse createNotice(NoticeRequest dto, Long userId, String role, Long regionId);
    NoticeResponse getNoticeDetail(Long noticeId, String role);
    List<NoticeListResponse> getNoticeList(String role, Long regionId);


    }
