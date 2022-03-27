package com.v4bot.ninth.floor.repositories;

import com.v4bot.ninth.floor.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesRepository extends JpaRepository<State, Long> {
}
