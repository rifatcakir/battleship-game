package com.battleship.gameengine.unit.utils;

import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.response.GameStatusResponse;
import org.assertj.core.api.AbstractAssert;

import java.util.UUID;

public class GameStatusResponseAssert extends AbstractAssert<GameStatusResponseAssert, GameStatusResponse> {

    public GameStatusResponseAssert(GameStatusResponse actual) {
        super(actual, GameStatusResponseAssert.class);
    }

    @Override
    public GameStatusResponseAssert isNotNull() {
        return super.isNotNull();
    }

    public GameStatusResponseAssert hasGivenGameId(UUID gameId) {
        if (!actual.getGameId().equals(gameId)) {
            failWithMessage("Expected gameId %s but was %s",
                    gameId, actual.getGameId());
        }
        return this;
    }

    public GameStatusResponseAssert hasGivenGameStatus(GameStatusDomain status) {
        if (!actual.getStatus().equals(status)) {
            failWithMessage("Expected %s but was %s",
                    status.name(), actual.getStatus().name());
        }
        return this;
    }

    public GameStatusResponseAssert hasGivenPlayer(CurrentPlayerDomain currentPlayerDomain) {
        if (!actual.getCurrentPlayer().equals(currentPlayerDomain)) {
            failWithMessage("Expected %s but was %s",
                    currentPlayerDomain.name(), actual.getCurrentPlayer().name());
        }
        return this;
    }

    public GameStatusResponseAssert hasGivenPlayerBoard(PlayerBoardDomain playerBoard) {
        if (!actual.getPlayerBoard().equals(playerBoard)) {
            failWithMessage("Expected playerBoard not matching");
        }
        return this;
    }
}