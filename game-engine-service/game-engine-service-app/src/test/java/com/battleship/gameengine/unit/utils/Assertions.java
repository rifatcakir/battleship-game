package com.battleship.gameengine.unit.utils;


import com.battleship.engine.model.response.GameStatusResponse;

public class Assertions {
    public static GameStatusResponseAssert assertThat(GameStatusResponse actual) {
        return new GameStatusResponseAssert(actual);
    }

}