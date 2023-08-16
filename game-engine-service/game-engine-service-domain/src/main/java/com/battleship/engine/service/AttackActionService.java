package com.battleship.engine.service;

import com.battleship.engine.model.request.ShipActionRequest;
import com.battleship.engine.model.request.ShipActionResponse;

public interface AttackActionService {
    ShipActionResponse executeAction(String name, ShipActionRequest shipAttackDomainModel);
}
