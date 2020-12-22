package fr.maner.adventofcode.day19;

import fr.maner.adventofcode.day19.rules.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleEngine {

    private final HashMap<Integer, Rule> rules = new HashMap<>();

    public RuleEngine(List<String> ruleInput) {
        ruleInput.forEach(this::parse);
    }

    public void parse(String line) {
        String[] colonSplit = line.split(":");

        int number = Integer.parseInt(colonSplit[0]);
        Rule newRule = from(this.rules, colonSplit[1].trim());

        this.rules.put(number, newRule);
    }

    public Rule from(Map<Integer, Rule> rules, String ruleRaw) {
        if (ruleRaw.contains("\"")) {
            return new Char(ruleRaw.replace('"', ' ').trim());
        } else if (ruleRaw.contains("|")) {
            List<Rule> subRules = toSubRules(ruleRaw.split("\\|"));
            return new Or(subRules);
        } else if (ruleRaw.contains(" ")) {
            List<Rule> subRules = toSubRules(ruleRaw.split(" "));
            return new And(subRules);
        } else {
            return new Basic(this, Integer.parseInt(ruleRaw.trim()));
        }
    }

    private List<Rule> toSubRules(String[] ruleTokens) {
        List<Rule> subRules = new ArrayList<>();

        for (String str : ruleTokens) {
            if (str.isEmpty()) continue;

            subRules.add(from(this.rules, str));
        }

        return subRules;
    }

    public boolean matches(Rule rule, String input) {
        return rule.getValidInput(input).contains(input);
    }

    public Map<Integer, Rule> getRules() {
        return this.rules;
    }
}
