package org.example.calc.tokenizer.tokens;

public class Sub extends Token {
    protected Sub() {
        super(2);
    }

    @Override
    public String toString() {
        return "SUB";
    }
}
