package com.battleship.engine.engine.rules;


import com.battleship.engine.engine.parameters.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RuleService {

    private final List<? extends Rule> rules;

    public void applyRule(Parameter param) {
        rules.forEach(rule -> rule.applyRule(param));
    }
}