package com.v4bot.ninth.floor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "states")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne
    @JoinColumn(name="strength_id")
    private Indicator strength;

    @OneToOne
    @JoinColumn(name="dexterity_id")
    private Indicator dexterity;

    @OneToOne
    @JoinColumn(name="iq_id")
    private Indicator iq;

    @OneToOne
    @JoinColumn(name="health_id")
    private Indicator health;

    @OneToOne
    @JoinColumn(name="fatigue_id")
    private Indicator fatigue;

    @OneToOne
    @JoinColumn(name="charisma_id")
    private Indicator charisma;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
