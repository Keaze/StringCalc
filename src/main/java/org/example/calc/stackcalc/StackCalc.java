package org.example.calc.stackcalc;

import org.example.Result;
import org.example.calc.tokenizer.Tokenizer;
import org.example.calc.tokenizer.tokens.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Deque;
import java.util.Objects;
import java.util.stream.Collectors;

public class StackCalc {
    public Result<BigDecimal, String> calculate(Deque<Token> calc){
        final Result<BigDecimal, String> result = resolve(calc);
        if(calc.isEmpty()){
            return result;
        } else {
            return Result.failure(String.format("Could not complete Calculation. Current Value [%s] Rest Stack %s", result, calc));
        }
    }

    private Result<BigDecimal, String> resolve(Deque<Token> deque){
        Token t = deque.pollLast();
        if(t instanceof Value){
            return Result.success(((Value) t).getValue());
        }
        if(t instanceof Add){
            final Result<BigDecimal, String> res1 = resolve(deque);
            final Result<BigDecimal, String> res2 = resolve(deque);
            return res1.map2(res2, BigDecimal::add).mapError(x -> String.join(", ", x));
        }
        if(t instanceof Sub){
            final Result<BigDecimal, String> res1 = resolve(deque);
            final Result<BigDecimal, String> res2 = resolve(deque);
            return res2.map2(res1, BigDecimal::subtract).mapError(x -> String.join(", ", x));
        }
        if(t instanceof Mul){
            final Result<BigDecimal, String> res1 = resolve(deque);
            final Result<BigDecimal, String> res2 = resolve(deque);
            return res1.map2(res2, BigDecimal::multiply).mapError(x -> String.join(", ", x));
        }
        if(t instanceof Div){
            final Result<BigDecimal, String> res1 = resolve(deque);
            final Result<BigDecimal, String> res2 = resolve(deque);
            return res2.map2(res1, BigDecimal::divide).mapError(x -> String.join(", ", x));
        }
        return Result.failure("Unexpected Token: " + t);
    }
}
