package com.battleship.gameengine.entity;

import com.battleship.gameengine.entity.enums.ShipTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ShipInfoEntity {
    private UUID shipGroupId;
    private ShipTypeEntity shipType;
}