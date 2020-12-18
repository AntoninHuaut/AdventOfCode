package fr.maner.adventofcode.day18;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ExpressionParser {

    private final String[] userInput;

    public ExpressionParser(boolean partTwo, String str) {
        ValueType.Add.setPriority(partTwo ? 1 : 0);
        ValueType.Multiply.setPriority(0);

        this.userInput = str
                .replace(ValueType.OpenParenthesis.getModel(), ValueType.OpenParenthesis.getModel() + " ")
                .replace(ValueType.CloseParenthesis.getModel(), " " + ValueType.CloseParenthesis.getModel())
                .split(" ");
    }

    // https://fr.wikipedia.org/wiki/Notation_polonaise_inverse
    public long resolveRPN() {
        LinkedList<String> priority = new LinkedList<>();

        for (String value : getRPNFormat()) {
            ValueType vType = get(value);
            String toPush = value;

            if (vType.isOperator()) {
                long first = Long.parseLong(priority.pop());
                long second = Long.parseLong(priority.pop());

                Long result = switch (vType) {
                    case Add -> first + second;
                    case Multiply -> first * second;
                    default -> null;
                };

                if (result == null) continue;

                toPush = "" + result;
            }

            priority.push(toPush);
        }

        return Long.parseLong(priority.pop());
    }

    // https://fr.wikipedia.org/wiki/Algorithme_Shunting-yard
    private String[] getRPNFormat() {
        List<String> rpnList = new ArrayList<>();
        LinkedList<String> priority = new LinkedList<>();

        for (String value : this.userInput) {
            ValueType vType = get(value);

            if (vType.isOperator()) {
                while (!priority.isEmpty()) {
                    ValueType peekType = get(priority.peek());

                    if (!peekType.isOperator() || isDifferentPriority(vType, peekType))
                        break;

                    rpnList.add(priority.pop());
                }

                priority.push(value);
            } else if (vType == ValueType.OpenParenthesis) {
                priority.push(value);
            } else if (vType == ValueType.CloseParenthesis) {
                while (!priority.isEmpty()) {
                    ValueType peekType = get(priority.peek());

                    if (peekType == ValueType.OpenParenthesis)
                        break;

                    rpnList.add(priority.pop());
                }
                priority.pop();
            } else {
                rpnList.add(value);
            }
        }

        rpnList.addAll(priority);

        return rpnList.toArray(new String[0]);
    }

    private boolean isDifferentPriority(ValueType first, ValueType second) {
        return (first.getPriority() - second.getPriority()) > 0;
    }

    private ValueType get(String input) {
        return Optional.ofNullable(ValueType.getByModel(input)).orElse(ValueType.Other);
    }
}