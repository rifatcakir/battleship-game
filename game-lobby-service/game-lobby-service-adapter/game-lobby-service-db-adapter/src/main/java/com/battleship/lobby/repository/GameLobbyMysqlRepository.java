package com.battleship.lobby.repository;

import com.battleship.lobby.entity.GameLobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameLobbyMysqlRepository extends JpaRepository<GameLobby, UUID> {
    @Query("select g from GameLobby g where g.player2Name is null")
    List<GameLobby> findAvailableGame();
}
