package org.example.calc.tokenizer.tokens;

public class Add extends Token {
    protected Add() {
        super(2);
    }

    @Override
    public String toString() {
        return "ADD";
    }
}
;