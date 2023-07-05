package org.example.calc;

import org.example.Result;
import org.example.calc.stackcalc.StackCalc;
import org.example.calc.tokenizer.Tokenizer;
import org.example.calc.transformer.Transformer;

import java.math.BigDecimal;


public class Calculator {
    private final Tokenizer tokenizer;
    private final Transformer transformer;
    private final StackCalc stackCalc;

    public Calculator(Tokenizer tokenizer, Transformer transformer, StackCalc stackCalc) {
        this.tokenizer = tokenizer;
        this.transformer = transformer;
        this.stackCalc = stackCalc;
    }

    public Result<BigDecimal, String> calculate(String input) {
        return tokenizer.tokenize(input).flatMap(transformer::transform).flatMap(stackCalc::calculate);
    }
}
