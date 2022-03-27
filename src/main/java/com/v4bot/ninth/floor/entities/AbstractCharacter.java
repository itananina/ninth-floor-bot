package com.v4bot.ninth.floor.entities;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne
    @JoinColumn(name="state_id")
    private State state;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getIndicatorsFormatted() {
        State state = this.getState();

        return "Сила "+ EmojiParser.parseToUnicode(":muscle:") +" "+ state.getCharisma().getScore() + "\n" +
                "Ловкость "+ EmojiParser.parseToUnicode(":dancer:") +" " + state.getDexterity().getScore() + "\n" +
                "Интеллект "+ EmojiParser.parseToUnicode(":bulb:") +" " + state.getIq().getScore() + "\n" +
                "Здоровье "+ EmojiParser.parseToUnicode(":heart:") +" " + state.getHealth().getScore() + "\n" +
                "Выносливость "+ EmojiParser.parseToUnicode(":chestnut:") +" " + state.getFatigue().getScore() + "\n" +
                "Харизма "+ EmojiParser.parseToUnicode(":clown:") +" " + state.getCharisma().getScore() + "\n";
    }
}
