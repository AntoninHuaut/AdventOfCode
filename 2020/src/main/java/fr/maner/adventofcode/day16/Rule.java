package fr.maner.adventofcode.day16;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule {

    private final String name;
    private final List<Integer> positionList = new ArrayList<>();
    private final Set<Condition> conditionSet = new HashSet<>();

    public Rule(String name) {
        this.name = name;
    }

    public static Rule parse(String line) {
        String[] parts = line.split(":");
        Rule rule = new Rule(parts[0]);

        String[] condArray = parts[1].split(" ");

        for (int i = 1; i < condArray.length; i++) {
            String cond = condArray[i];
            if (!cond.contains("-")) continue;

            rule.addCondition(Condition.parse(cond));
        }

        return rule;
    }

    public List<Integer> getPositionList() {
        return this.positionList;
    }

    public void addPosition(Integer position) {
        this.positionList.add(position);
    }

    public void removePosition(Integer position) {
        this.positionList.remove(position);
    }

    public boolean contains(long value) {
        return this.conditionSet.stream().anyMatch(cond -> cond.contains(value));
    }

    public String getName() {
        return this.name;
    }

    public void addCondition(Condition cond) {
        this.conditionSet.add(cond);
    }
}
