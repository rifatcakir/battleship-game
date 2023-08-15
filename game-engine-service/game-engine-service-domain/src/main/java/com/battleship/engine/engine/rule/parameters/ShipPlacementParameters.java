package com.battleship.engine.engine.rule.parameters;

import com.battleship.engine.engine.rule.model.CellPosition;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShipPlacementParameters extends Parameter {
    private PlayerBoardDomain playerBoardDomain;
    private ShipType shipType;
    private List<CellPosition> positions;
}
