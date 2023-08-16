package com.battleship.engine.model;

import com.battleship.engine.model.enums.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ActionStatus {
    private String shipType;
    private ActionResult actionResult;
}
