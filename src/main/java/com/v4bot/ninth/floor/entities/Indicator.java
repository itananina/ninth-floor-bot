package com.v4bot.ninth.floor.entities;

import com.v4bot.ninth.floor.enums.IndicatorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "indicators")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="score")
    private Integer score;

    @Column(name="is_locked")
    private Boolean isLocked;

    @Column(name="type")
    private IndicatorType type;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
