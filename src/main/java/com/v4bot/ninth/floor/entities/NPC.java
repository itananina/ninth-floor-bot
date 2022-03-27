package com.v4bot.ninth.floor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "npcs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NPC extends AbstractCharacter {

    @ManyToOne
    @JoinColumn(name="archetype_id")
    private Archetype archetype;

    @Column(name="greeting_phrase")
    private Integer greetingPhrase;

    @OneToOne
    @JoinColumn(name="location_id")
    private Location location;

}
