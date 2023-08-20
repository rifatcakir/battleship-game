package com.battleship.engine.model;

import com.battleship.engine.model.enums.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ActionStatus implements Serializable {
    private String shipType;
    private ActionResult actionResult;
}
