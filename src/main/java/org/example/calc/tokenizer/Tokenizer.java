package org.example.calc.tokenizer;

import org.example.Result;
import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Tokens;

import java.util.*;

import static org.example.calc.tokenizer.tokens.Tokens.TOKENTYPS.*;

public class Tokenizer {
    private String[] symbols;
    private final Deque<Token> tokens;
    private Set<String> operators;
    private Set<String> whitespace;
    private String delimter;
    private String decimalPoint;
    public Tokenizer(){
        operators= Set.of(ADD.symbol, SUB.symbol, DIV.symbol, MUL.symbol);
        whitespace= Set.of(" ", "   ");
        delimter = ",";
        decimalPoint = ".";
        i = 0;
        tokens = new LinkedList<>();
    }

    private int i;




    public Result<Deque<Token>, String> tokenize(String input){
        i = 0;
        this.symbols = input.split("");
        return tokenize();
    }
    private Result<Deque<Token>, String> tokenize(){
        try {
            while(i < symbols.length){
                final String s = symbols[i];
                if (operators.contains(s)) {
                    tokens.add(getToken(s));
                    continue;
                }
                if(isNumeric(s)){
                    tokens.add(readNumber());
                    continue;
                }
                if(whitespace.contains(s)){
                    i++;
                    continue;
                }
                return Result.failure("Invalid Symbol [%s]".formatted(s));
            }
            return Result.success(tokens);
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    private Token readNumber() {
        double value = 0;
        double multiplyer = 10;
        boolean decimal = false;
        while(i < symbols.length){
            final String s = symbols[i];
            if(isNumeric(s)){
                final double temp = Double.parseDouble(s);
                if(!decimal){
                    value = (value * multiplyer) + temp;
                } else {
                    value = value + (temp * multiplyer);
                    multiplyer *= 0.1;
                }
                i++;
                continue;
            }
            if(Objects.equals(s, decimalPoint)){
                decimal = true;
                multiplyer = 0.1;
                i++;
                continue;
            }
            if(Objects.equals(s, delimter)){
                i++;
                continue;
            }
            return Tokens.value(value);
        }
        i++;
        return Tokens.value(value);
    }

    private Token getToken(String t) {
        i++;
        return switch (t) {
            case "+" -> Tokens.add();
            case "-" -> Tokens.sub();
            case "*" -> Tokens.mul();
            case "/" -> Tokens.div();
            default -> throw new IllegalArgumentException("No such operator " + t);
        };
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
