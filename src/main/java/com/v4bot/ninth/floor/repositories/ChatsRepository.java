package com.v4bot.ninth.floor.repositories;

import com.v4bot.ninth.floor.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatsRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findChatById(Long id);
}
