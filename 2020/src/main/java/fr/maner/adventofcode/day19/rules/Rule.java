package fr.maner.adventofcode.day19.rules;

import java.util.ArrayList;
import java.util.List;

public abstract class Rule {

    protected List<Rule> subRules;

    public Rule() {
        this.subRules = new ArrayList<>();
    }

    public Rule(List<Rule> subRules) {
        this.subRules = subRules;
    }

    public abstract List<String> getValidInput(String input);
}
