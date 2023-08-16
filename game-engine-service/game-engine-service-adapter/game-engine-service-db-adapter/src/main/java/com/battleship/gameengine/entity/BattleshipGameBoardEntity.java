package com.battleship.gameengine.entity;

import com.battleship.gameengine.entity.enums.CurrentTurn;
import com.battleship.gameengine.entity.enums.GameStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Document("battleshipGameBoard")
@Getter
@Setter
@NoArgsConstructor
public class BattleshipGameBoardEntity {
    @Id
    private UUID gameId;
    private List<PlayerBoardEntity> playerBoards;
    private CurrentTurn currentTurn;
    private GameStatus status;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
}

