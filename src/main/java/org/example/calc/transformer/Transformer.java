package org.example.calc.transformer;

import org.example.Result;
import org.example.calc.tokenizer.tokens.Operator;
import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Value;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class Transformer {
    private final Deque<Operator> operators;

    public Transformer() {

        operators = new LinkedList<>();
    }

    public Result<Deque<Token>, String> transform(Deque<Token> tokens) {
        operators.clear();
        final Deque<Token> result = new LinkedList<>();
        boolean lastTokenValue = false;
        while (tokens.peek() != null) {
            final Token t = tokens.poll();
            if (t instanceof Value) {
                if (lastTokenValue) return Result.failure("Invalid Input on " + t);
                result.offer(t);
                lastTokenValue = true;
            } else if (t instanceof Operator op) {
                while (op.getPrecedence() <= Optional.ofNullable(operators.peek()).map(Operator::getPrecedence).orElse(-1)) {
                    final Token op2 = operators.poll();
                    result.offer(op2);
                }
                operators.add(op);
                lastTokenValue = false;
            } else {
                return Result.failure("Invalid Input on " + t);
            }

        }
        while (operators.peek() != null){
            result.offer(operators.removeLast());
        }
        return Result.success(result);
    }
}
