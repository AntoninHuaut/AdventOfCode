package fr.maner.adventofcode.day19.rules;

import java.util.ArrayList;
import java.util.List;

public class Char extends Rule {

    private String s;

    public Char(String s) {
        this.s = s;
    }

    @Override
    public List<String> getValidInput(String input) {
        List<String> values = new ArrayList<>();

        if (input.startsWith(this.s)) {
            values.add(input.substring(0, this.s.length()));
        }

        return values;
    }
}
