package org.example.calc.stackcalc;

import org.example.Result;
import org.example.calc.tokenizer.tokens.Operator;
import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Value;

import java.math.BigDecimal;
import java.util.Deque;

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
        if (t instanceof Value) {
            return Result.success(((Value) t).getValue());
        }
        if (t instanceof Operator op) {
            final Result<BigDecimal, String> res1 = resolve(deque);
            final Result<BigDecimal, String> res2 = resolve(deque);
            return res2.map2(res1, op.getF()).mapError(x -> String.join(", ", x));
        }
        return Result.failure("Unexpected Token: " + t);
    }
}
