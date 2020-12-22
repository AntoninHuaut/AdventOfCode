package fr.maner.adventofcode.day19.rules;

import java.util.ArrayList;
import java.util.List;

public class And extends Rule {

    public And(List<Rule> subRules) {
        super(subRules);
    }

    @Override
    public List<String> getValidInput(String input) {
        List<String> values = this.subRules.get(0).getValidInput(input);

        for (int i = 1; i < this.subRules.size(); i++) {
            List<String> nextValues = new ArrayList<>();

            for (String value : values) {
                List<String> validValues = new ArrayList<>();
                String subStr = input.substring(value.length());

                for (String str : this.subRules.get(i).getValidInput(subStr)) {
                    validValues.add(value + str);
                }

                nextValues.addAll(validValues);
            }

            values = nextValues;
        }

        return values;
    }
}
