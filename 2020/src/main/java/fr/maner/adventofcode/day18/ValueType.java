package fr.maner.adventofcode.day18;

import java.util.HashMap;

public enum ValueType {

    Add("+", true),
    Multiply("*", true),
    OpenParenthesis("(", false),
    CloseParenthesis(")", false),
    Other(null, false);

    private static final HashMap<String, ValueType> modelToValueType = new HashMap<>();

    static {
        for (ValueType vType : values()) {
            modelToValueType.put(vType.getModel(), vType);
        }
    }

    private final String model;
    private final boolean operator;
    private int priority = 0;

    ValueType(String model, boolean operator) {
        this.model = model;
        this.operator = operator;
    }

    public static ValueType getByModel(String input) {
        return modelToValueType.getOrDefault(input, null);
    }

    public boolean isOperator() {
        return this.operator;
    }

    public String getModel() {
        return this.model;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
