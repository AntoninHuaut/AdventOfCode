package fr.maner.adventofcode.day19.rules;

import fr.maner.adventofcode.day19.RuleEngine;

import java.util.List;

public class Basic extends Rule {

    private final RuleEngine ruleEngine;
    private final Integer id;

    public Basic(RuleEngine ruleEngine, Integer id) {
        this.ruleEngine = ruleEngine;
        this.id = id;
    }

    @Override
    public List<String> getValidInput(String input) {
        return this.ruleEngine.getRules().get(this.id).getValidInput(input);
    }
}