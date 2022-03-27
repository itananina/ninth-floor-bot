package com.v4bot.ninth.floor.entities;

import com.v4bot.ninth.floor.enums.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "missions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name="main_player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name="npc_id")
    private NPC npc;

    @Column(name = "status")
    private MissionStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
