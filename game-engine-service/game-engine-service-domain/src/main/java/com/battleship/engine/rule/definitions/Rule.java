package com.battleship.engine.rule.definitions;

import com.battleship.engine.rule.parameters.Parameter;

/**
 * The Rule interface represents a specific rule or logic that can be applied in a game.
 * Implementing classes define how the rule is executed when applied to a given context.
 */
public interface Rule {
    /**
     * Apply the defined rule to the provided parameter.
     *
     * @param parameter The parameter containing information and context for the rule application.
     */
    void applyRule(Parameter parameter);
}