package org.example.calc.tokenizer.tokens;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Tokens {
    private static final Set<Operator> OPERATOR_SET = Arrays.stream(OperatorEnum.values()).map(Operator::new).collect(Collectors.toSet());
    private static final Map<String, Operator> OPERATOR_MAP = OPERATOR_SET.stream().collect(Collectors.toMap(Operator::getSymbol, x -> x));

    private Tokens() {
    }

    public static Set<Operator> getStandard() {
        return OPERATOR_SET;
    }

    public static Set<Operator> getWithoutPrecedence() {
        return Arrays.stream(OperatorEnum.values()).map(x -> new Operator(1, x.getSymbol(), x.getAssociativity(), x.getF())).collect(Collectors.toSet());
    }

    public static Operator add() {
        return OPERATOR_MAP.get(OperatorEnum.ADD.getSymbol());
    }

    public static Operator sub() {
        return OPERATOR_MAP.get(OperatorEnum.SUB.getSymbol());
    }

    public static Operator mul() {
        return OPERATOR_MAP.get(OperatorEnum.MUL.getSymbol());
    }

    public static Operator div() {
        return OPERATOR_MAP.get(OperatorEnum.DIV.getSymbol());
    }

    public static Token value(double value) {
        return Value.create(value);
    }
}
