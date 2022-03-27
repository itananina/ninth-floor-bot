package com.v4bot.ninth.floor.repositories;

import com.v4bot.ninth.floor.entities.PlayableCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayableCharactersRepository extends JpaRepository<PlayableCharacter, Long> {
}
