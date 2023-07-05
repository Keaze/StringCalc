package org.example.calc.transformer;

import org.example.Result;
import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Value;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class Transformer {
    private final Deque<Token> operators;

    public Transformer(){

        operators = new LinkedList<>();
    }

    public Result<Deque <Token>,String> transform(Deque <Token> tokens){
        final Deque <Token> result = new LinkedList<>();
        while (tokens.peek() != null){
            final Token t = tokens.poll();
            if(t instanceof Value){
                result.offer(t);
            } else {
                while (t.getPrecedence() <= Optional.ofNullable(operators.peek()).map(Token::getPrecedence).orElse(-1)){
                    final Token op = operators.poll();
                    result.offer(op);
                }
                operators.add(t);
            }

        }
        while (operators.peek() != null){
            result.offer(operators.removeLast());
        }
        return Result.success(result);
    }
}
