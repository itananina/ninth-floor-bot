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
@Table(name = "archetypes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Archetype extends AbstractCharacter {

    @Column(name="play_frequency")
    private Integer playFrequency;

}
