package com.moyeorak.content_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(
        name = "facilities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "region_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String location;

    @Column(length = 255, nullable = false)
    private String address;

    @Column(length = 50)
    private String contact;

    @Column(length = 255)
    private String imageUrl;

    @Column(nullable = false)
    private Integer capacity;

    @Column(length = 1000) // 또는 @Lob, 필요에 따라
    private String description;

    @Column
    private Integer area;

    @Column(name = "usage_start_time", nullable = false)
    private LocalTime usageStartTime;

    @Column(name = "usage_end_time", nullable = false)
    private LocalTime usageEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}