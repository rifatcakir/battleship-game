package com.battleship.engine.rule.definitions;

import com.battleship.engine.rule.parameters.Parameter;

public interface Rule {
    void applyRule(Parameter parameter);
}