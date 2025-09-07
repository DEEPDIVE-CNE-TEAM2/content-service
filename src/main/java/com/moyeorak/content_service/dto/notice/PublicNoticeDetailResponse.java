package com.moyeorak.content_service.dto.notice;

import com.moyeorak.content_service.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PublicNoticeDetailResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int viewCount;

    public static PublicNoticeDetailResponse from(Notice notice) {
        return PublicNoticeDetailResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .viewCount(notice.getViewCount())
                .build();
    }
}