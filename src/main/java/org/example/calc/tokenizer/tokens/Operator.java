package org.example.calc.tokenizer.tokens;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class Operator implements Token {
    final int precedence;
    final String symbol;
    final ASSOCIATIVITY associativity;
    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> f;


    public Operator(OperatorEnum t) {
        precedence = t.getPrecedence();
        symbol = t.getSymbol();
        associativity = t.getAssociativity();
        f = t.getF();
    }

    public Operator(int precedence, String symbol, ASSOCIATIVITY associativity, BiFunction<BigDecimal, BigDecimal, BigDecimal> f) {
        this.precedence = precedence;
        this.symbol = symbol;
        this.associativity = associativity;
        this.f = f;
    }

    public int getPrecedence() {
        return precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return this.getSymbol();
    }

    public ASSOCIATIVITY getAssociativity() {
        return associativity;
    }

    public BiFunction<BigDecimal, BigDecimal, BigDecimal> getF() {
        return f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator operator)) return false;

        if (precedence != operator.precedence) return false;
        if (!symbol.equals(operator.symbol)) return false;
        return associativity == operator.associativity;
    }

    @Override
    public int hashCode() {
        int result = precedence;
        result = 31 * result + symbol.hashCode();
        result = 31 * result + associativity.hashCode();
        return result;
    }
}
