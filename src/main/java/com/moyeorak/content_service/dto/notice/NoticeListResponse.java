package com.moyeorak.content_service.dto.notice;


import com.moyeorak.content_service.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class NoticeListResponse {

    private Long id;
    private String title;
    private Long authorId;
    private LocalDate createdDate;
    private int viewCount;

    public static NoticeListResponse from(Notice notice) {
        return NoticeListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .authorId(notice.getAuthorId())
                .createdDate(notice.getCreatedAt().toLocalDate()) // 날짜만
                .viewCount(notice.getViewCount())
                .build();
    }
}