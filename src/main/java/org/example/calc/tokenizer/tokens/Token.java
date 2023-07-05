package org.example.calc.tokenizer.tokens;

import java.util.Objects;

public abstract class Token {

    protected int precedence;
    protected Token(int precedence){
        this.precedence = precedence;
    }
    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.getClass(), obj.getClass());
    }

    public int getPrecedence(){
        return precedence;
    }

}
