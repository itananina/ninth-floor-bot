package com.v4bot.ninth.floor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "characters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayableCharacter extends AbstractCharacter {

    @ManyToOne
    @JoinColumn(name="archetype_id")
    private Archetype archetype;

    @Column(name="name")
    private String name;

    public PlayableCharacter(Archetype chosen) {
        this.archetype = chosen;
    }
}
