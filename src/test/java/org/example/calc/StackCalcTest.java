package org.example.calc;

import org.example.Result;
import org.example.calc.stackcalc.StackCalc;
import org.example.calc.tokenizer.Tokenizer;
import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Tokens;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class StackCalcTest {
    @Test
    void readCalculation() {
        final Deque<Token> queue = new LinkedList<>();
        queue.add(Tokens.value(3));
        queue.add(Tokens.value(4));
        queue.add(Tokens.value(2));
        queue.add(Tokens.mul());
        queue.add(Tokens.add());
        final Result<BigDecimal, String> result = new StackCalc().calculate(queue);
        assertEquals(Result.success(11.0), result.map(BigDecimal::doubleValue));
    }
}