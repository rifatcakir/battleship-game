package com.battleship.engine.model;

import com.battleship.engine.model.enums.ActionResult;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActionStatus {
    private String shipType;
    private ActionResult actionResult;
}
