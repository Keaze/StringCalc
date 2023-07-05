package org.example.calc;

import org.example.Result;
import org.example.calc.stackcalc.StackCalc;
import org.example.calc.tokenizer.Tokenizer;
import org.example.calc.transformer.Transformer;

import java.math.BigDecimal;

public class Calculator {
    private Tokenizer tokenizer;
    private Transformer transformer;
    private StackCalc stackCalc;

    public Calculator(){
        tokenizer = new Tokenizer();
        transformer = new Transformer();
        stackCalc = new StackCalc();
    }

    public Result<BigDecimal, String> calculate(String input){
        return tokenizer.tokenize(input).flatMap(transformer::transform).flatMap(stackCalc::calculate);
    }
}
