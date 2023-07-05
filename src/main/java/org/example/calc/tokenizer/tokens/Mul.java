package org.example.calc.tokenizer.tokens;

public class Mul extends Token{
    protected Mul() {
        super(3);
    }

    @Override
    public String toString() {
        return "MUL";
    }
}
