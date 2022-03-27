package com.v4bot.ninth.floor.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chats")
@Data
public class Chat {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="pr_user")
    private String primaryUser;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @ManyToMany
//    @JoinTable(
//            name="chats_players",
//            joinColumns = @JoinColumn(name="chat_id"),
//            inverseJoinColumns = @JoinColumn(name="player_id")
//    )
//    private List<Player> playerList;

    public Chat(Long id, String primaryUser) {
        this.id = id;
        this.primaryUser = primaryUser;
    }

    public Chat() {

    }

}
