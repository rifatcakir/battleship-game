package com.battleship.gameengine.util;

import com.battleship.engine.model.BoardCell;
import com.battleship.gameengine.entity.BoardCellEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardCellMapper {

    BoardCellMapper INSTANCE = Mappers.getMapper(BoardCellMapper.class);

    BoardCell entityBoardCellToDomain(BoardCellEntity destination);

    BoardCellEntity domainBoardCellToEntity(BoardCell destination);
}