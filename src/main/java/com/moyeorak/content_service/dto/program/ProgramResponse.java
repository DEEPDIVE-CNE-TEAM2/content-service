package com.moyeorak.content_service.dto.program;

import com.moyeorak.content_service.entity.Program;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramResponse {
    private Long id;
    private String title;
    private String regionName;
    private String facilityName;

    public static ProgramResponse from(Program program) {
        return ProgramResponse.builder()
                .id(program.getId())
                .title(program.getTitle())
                .regionName(program.getRegion().getName())
                .facilityName(program.getFacility().getName())
                .build();
    }
}
