package org.example.calc.tokenizer.tokens;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import static org.example.calc.tokenizer.tokens.ASSOCIATIVITY.LEFT;

public enum OperatorEnum {
    ADD("+", 2, LEFT, BigDecimal::add),
    SUB("-", 2, LEFT, BigDecimal::subtract),
    MUL("*", 3, LEFT, BigDecimal::multiply),
    DIV("/", 3, LEFT, BigDecimal::divide),
    ;

    private final String symbol;
    private final int precedence;
    private final ASSOCIATIVITY associativity;
    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> f;

    OperatorEnum(String s, int i, ASSOCIATIVITY associativity, BiFunction<BigDecimal, BigDecimal, BigDecimal> f) {
        this.symbol = s;
        this.precedence = i;
        this.associativity = associativity;
        this.f = f;
    }

    public BiFunction<BigDecimal, BigDecimal, BigDecimal> getF() {
        return f;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public ASSOCIATIVITY getAssociativity() {
        return associativity;
    }
}
