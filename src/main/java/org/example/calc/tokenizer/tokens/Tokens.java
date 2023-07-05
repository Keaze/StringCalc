package org.example.calc.tokenizer.tokens;

import java.math.BigDecimal;
import java.util.Map;

import static org.example.calc.tokenizer.tokens.Tokens.TOKENTYPS.*;

public class Tokens {

    public enum TOKENTYPS {
        ADD("+"),
        SUB("-"),
        MUL("*"),
        DIV("/"),
        VALUE("");

        public final String symbol;

        TOKENTYPS(String s) {
            this.symbol = s;
        }
    }

    private static Map<TOKENTYPS, Token> tokens = Map.of(
            ADD, new Add(),
            SUB, new Sub(),
            MUL, new Mul(),
            DIV, new Div()
    );

    private Tokens() {};

    public static Token add() {
        return getToken(ADD);
    }
    public static Token sub() {
        return getToken(SUB);
    }    public static Token mul() {
        return getToken(MUL);
    }    public static Token div() {
        return getToken(DIV);
    }
    public static Token value(BigDecimal value) {
        return Value.create(value);
    }
    public static Token value(int value) {
        return Value.create(value);
    }
    public static Token value(String value) {
        return Value.create(value);
    }
    public static Token value(double value) {
        return Value.create(BigDecimal.valueOf(value));
    }

    private static Token getToken(TOKENTYPS typ){
        return tokens.get(typ);
    }
}
