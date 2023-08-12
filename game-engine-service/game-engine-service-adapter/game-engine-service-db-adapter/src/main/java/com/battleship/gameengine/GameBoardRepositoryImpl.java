package com.battleship.gameengine;

import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.gameengine.entity.User;
import com.battleship.gameengine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameBoardRepositoryImpl implements GameBoardRepository {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createGameBoard() {
        userRepository.save(new User(UUID.randomUUID(), "aaa", "bb"));
        userRepository.save(new User(UUID.randomUUID(), "aaa2", "bb"));
        userRepository.findAll().forEach(it -> System.out.println(it.getId() + "--" + it.getFirstName()));

    }
}
