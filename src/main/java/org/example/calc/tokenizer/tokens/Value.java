package org.example.calc.tokenizer.tokens;

import java.math.BigDecimal;

public class Value extends Token{
    private final BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    private Value(BigDecimal value){
        super(-1);
        this.value = value;
    }

    public static Value create(BigDecimal value) {
        return new Value(value);
    }

    public static Value create(double value) {
        return new Value(BigDecimal.valueOf(value));
    }

    public static Value create(String value) {
        return new Value(BigDecimal.valueOf(Long.parseLong(value)));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value value1)) return false;
        if (!super.equals(o)) return false;

        return value.equals(value1.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
