package com.battleship.engine.service;

import com.battleship.engine.model.request.ShipActionRequest;
import com.battleship.engine.model.response.ShipActionResponse;

public interface AttackActionService {
    ShipActionResponse executeAction(String name, ShipActionRequest shipAttackDomainModel);
}
