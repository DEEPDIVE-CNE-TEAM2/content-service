package com.moyeorak.content_service.dto.notice;

import com.moyeorak.content_service.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PublicNoticeListResponse {
    private Long id;
    private String title;
    private LocalDate createdAt;
    private int viewCount;

    public static PublicNoticeListResponse from(Notice notice) {
        return PublicNoticeListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt().toLocalDate())
                .viewCount(notice.getViewCount())
                .build();
    }
}