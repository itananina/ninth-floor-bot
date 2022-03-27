package com.v4bot.ninth.floor.repositories;

import com.v4bot.ninth.floor.entities.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionsRepository extends JpaRepository<Mission, Long> {

    List<Mission> findAllByChatIdOrderByUpdatedAtDesc(Long chatId);
}
