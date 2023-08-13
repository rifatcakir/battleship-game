package com.battleship.gameengine.repository;

import com.battleship.gameengine.entity.BattleshipGameBoardEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleshipGameBoardRepository extends MongoRepository<BattleshipGameBoardEntity, Integer> {
}