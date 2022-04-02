package com.v4bot.ninth.floor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "archetypes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Archetype extends AbstractCharacter {

    @Column(name="play_frequency")
    private Integer playFrequency;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="is_playable")
    private Boolean isPlayable;

}
