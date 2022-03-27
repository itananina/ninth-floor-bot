package com.v4bot.ninth.floor.repositories;

import com.v4bot.ninth.floor.entities.Archetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchetypesRepository extends JpaRepository<Archetype, Long> {
    List<Archetype> findAllByOrderByPlayFrequencyAsc();
}
