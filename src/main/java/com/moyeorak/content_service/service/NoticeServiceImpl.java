package com.moyeorak.content_service.service;

import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.common.exception.ErrorCode;
import com.moyeorak.content_service.common.AdminAuthHelper;
import com.moyeorak.content_service.dto.notice.NoticeListResponse;
import com.moyeorak.content_service.dto.notice.NoticeRequest;
import com.moyeorak.content_service.dto.notice.NoticeResponse;
import com.moyeorak.content_service.entity.Notice;
import com.moyeorak.content_service.entity.Region;
import com.moyeorak.content_service.repository.NoticeRepository;
import com.moyeorak.content_service.repository.RegionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class NoticeServiceImpl implements NoticeService{

    private final RegionRepository regionRepository;
    private final AdminAuthHelper adminAuthHelper;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public NoticeResponse createNotice(NoticeRequest dto, Long userId, String role, Long regionId) {
        adminAuthHelper.validateAdmin(role);

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));

        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .authorId(userId)
                .region(region)
                .build();


        return NoticeResponse.from(noticeRepository.save(notice));
    }

    // 관리자 공지사항 리스트 조회
    @Transactional(readOnly = true)
    public List<NoticeListResponse> getNoticeList(String role, Long regionId) {
        adminAuthHelper.validateAdmin(role);

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));

        return noticeRepository.findByRegion(region).stream()
                .map(NoticeListResponse::from)
                .toList();
    }

    // 관리자 공지사항 상세보기
    @Transactional(readOnly = true)
    public NoticeResponse getNoticeDetail(Long noticeId, String role) {
        adminAuthHelper.validateAdmin(role);

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_NOTICE));

        return NoticeResponse.from(notice);
    }

}
