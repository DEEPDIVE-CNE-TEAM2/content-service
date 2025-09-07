package com.moyeorak.content_service.dto.notice;


import com.moyeorak.content_service.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NoticeResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int viewCount;
    private Long authorId;

    public static NoticeResponse from(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .viewCount(notice.getViewCount())
                .authorId(notice.getAuthorId())
                .build();
    }
}