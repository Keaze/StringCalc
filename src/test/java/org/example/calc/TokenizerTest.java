package org.example.calc;

import org.example.calc.tokenizer.Tokenizer;
import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Tokens;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TokenizerTest {

    @Test
    void readNumeral() {
        final Queue<Token> res = new Tokenizer(Tokens.getStandard(), Set.of(" ", "   "), ".").tokenize("5").getOrThrow();
        assertArrayEquals(res.toArray(), List.of(Tokens.value(5.0)).toArray());
    }

    @Test
    void readNumber() {
        final Queue<Token> res = new Tokenizer(Tokens.getStandard(), Set.of(" ", "   "), ".").tokenize("5523145").getOrThrow();
        assertArrayEquals(res.toArray(), List.of(Tokens.value(5523145.0)).toArray());
    }

    @Test
    void readDecimal() {
        final Queue<Token> res = new Tokenizer(Tokens.getStandard(), Set.of(" ", "   "), ".").tokenize("552.3145").getOrThrow();
        assertArrayEquals(res.toArray(), List.of(Tokens.value(552.3145)).toArray());
    }

    @Test
    void readAddOperator() {
        final Queue<Token> res = new Tokenizer(Tokens.getStandard(), Set.of(" ", "   "), ".").tokenize("+").getOrThrow();
        assertArrayEquals(res.toArray(), List.of(Tokens.add()).toArray());
    }

    @Test
    void readMultipleOperator() {
        final Queue<Token> res = new Tokenizer(Tokens.getStandard(), Set.of(" ", "   "), ".").tokenize("-+-+-+-+-+-+-").getOrThrow();
        assertArrayEquals(res.toArray(), List.of(Tokens.sub(), Tokens.add(), Tokens.sub(), Tokens.add(), Tokens.sub(), Tokens.add(), Tokens.sub(), Tokens.add(), Tokens.sub(), Tokens.add(), Tokens.sub(), Tokens.add(), Tokens.sub()).toArray());
    }

    @Test
    void readCalculation() {
        final Queue<Token> res = new Tokenizer(Tokens.getStandard(), Set.of(" ", "   "), ".").tokenize("3 + 2 * 5").getOrThrow();
        final List<Token> expected = List.of(Tokens.value(3), Tokens.add(), Tokens.value(2), Tokens.mul(), Tokens.value(5));
        assertArrayEquals(expected.toArray(), res.toArray());
    }
}