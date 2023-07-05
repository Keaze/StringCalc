package org.example.calc;

import org.example.calc.stackcalc.StackCalc;
import org.example.calc.tokenizer.Tokenizer;
import org.example.calc.tokenizer.tokens.Tokens;
import org.example.calc.transformer.Transformer;

public class Calculators {
    /**
     * Creates a calculator with normal operator precedence
     *
     * @return Calculator
     */
    public static Calculator standardCalculator() {
        return new Calculator(new Tokenizer(Tokens.getStandard(), Tokenizer.WHITESPACE, Tokenizer.POINT), new Transformer(), new StackCalc());
    }

    /**
     * Creates a calculator with normal operator precedence
     * For example 1 + 2 * 3 = 9 instead of 7
     *
     * @return Calculator
     */
    public static Calculator calculatorWithoutPrecedence() {
        return new Calculator(new Tokenizer(Tokens.getWithoutPrecedence(), Tokenizer.WHITESPACE, Tokenizer.POINT), new Transformer(), new StackCalc());
    }
}
