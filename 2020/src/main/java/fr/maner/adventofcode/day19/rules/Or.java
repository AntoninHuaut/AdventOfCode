package fr.maner.adventofcode.day19.rules;

import java.util.ArrayList;
import java.util.List;

public class Or extends Rule {

    public Or(List<Rule> subRules) {
        super(subRules);
    }

    @Override
    public List<String> getValidInput(String input) {
        List<String> values = new ArrayList<>();

        for (Rule rule : this.subRules) {
            values.addAll(rule.getValidInput(input));
        }

        return values;
    }
}
