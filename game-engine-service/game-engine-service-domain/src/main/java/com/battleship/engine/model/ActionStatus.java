package com.battleship.engine.model;

import com.battleship.engine.model.enums.ActionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The ActionStatus class represents the status of a game action, including information about a ship's type
 * and the result of the action. This class is Serializable to allow for object serialization.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ActionStatus implements Serializable {

    /**
     * The type of the ship associated with the action status.
     */
    private String shipType;

    /**
     * The result of the game action, indicating an outcome like HIT, MISS, or PLACED.
     */
    private ActionResult actionResult;
}

