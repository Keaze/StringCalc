package org.example.calc.tokenizer;

import org.example.Result;
import org.example.calc.tokenizer.tokens.Operator;
import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Tokens;

import java.util.*;
import java.util.stream.Collectors;

public class Tokenizer {

    public static Set<String> WHITESPACE = Set.of(" ", "   ");
    public static String POINT = ".";
    public static String COMMA = ".";
    private final Map<String, Operator> symbolMap;
    private final Deque<Token> tokens;
    private final Set<String> whitespace;
    private final String decimalPoint;
    private String[] symbols;
    private int i;

    public Tokenizer(Set<Operator> operators, Set<String> whitespace, String decimalPoint) {
        this.symbolMap = operators.stream().collect(Collectors.toMap(Operator::getSymbol, x -> x));
        this.whitespace = whitespace;
        this.decimalPoint = decimalPoint;
        i = 0;
        tokens = new LinkedList<>();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public Result<Deque<Token>, String> tokenize(String input) {
        i = 0;
        tokens.clear();
        this.symbols = input.split("");
        return tokenize();
    }

    private Result<Deque<Token>, String> tokenize() {
        try {
            while (i < symbols.length) {
                final String s = symbols[i];
                if (symbolMap.containsKey(s)) {
                    tokens.add(getToken(s));
                    continue;
                }
                if (isNumeric(s)) {
                    tokens.add(readNumber());
                    continue;
                }
                if (whitespace.contains(s)) {
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
        double multiplier = 10;
        boolean decimal = false;
        while (i < symbols.length) {
            final String s = symbols[i];
            if (isNumeric(s)) {
                final double temp = Double.parseDouble(s);
                if (!decimal) {
                    value = (value * multiplier) + temp;
                } else {
                    value = value + (temp * multiplier);
                    multiplier *= 0.1;
                }
                i++;
                continue;
            }
            if (Objects.equals(s, decimalPoint)) {
                if (!decimal) {
                    decimal = true;
                    multiplier = 0.1;
                    i++;
                    continue;
                } else {
                    throw new IllegalStateException("Invalid Decimal Point");
                }

            }
            return Tokens.value(value);
        }
        i++;
        return Tokens.value(value);
    }

    private Token getToken(String t) {
        i++;
        if (!symbolMap.containsKey(t)) {
            throw new IllegalArgumentException("No such operator " + t);
        }
        return symbolMap.get(t);
    }
}
