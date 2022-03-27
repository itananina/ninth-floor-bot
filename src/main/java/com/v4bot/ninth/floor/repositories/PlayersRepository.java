package com.v4bot.ninth.floor.repositories;

import com.v4bot.ninth.floor.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayersRepository extends JpaRepository<Player, Long> {
    Optional<Player> findPlayerByUsername(String username);

    @Query("select pl from Player pl left join fetch pl.chatSet where pl.id = ?1")
    List<Player> findPlayerByIdExt(Long id);

    @Query(value = "select p from Player p left join fetch p.chatSet c where c.id = ?1")
    List<Player> findPlayersByChatId(Long id);
}
