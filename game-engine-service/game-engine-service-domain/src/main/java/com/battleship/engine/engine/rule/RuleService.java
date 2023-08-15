package com.battleship.engine.engine.rule;


import com.battleship.engine.engine.rule.parameters.Parameter;
import com.battleship.engine.engine.rule.rules.Rule;
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