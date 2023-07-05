package org.example.calc.transformer;

import org.example.calc.tokenizer.tokens.Token;
import org.example.calc.tokenizer.tokens.Tokens;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TransformerTest {
    @Test
    void transformAddition() {
        Deque<Token> test = new LinkedList<>();
        test.add(Tokens.value(5));
        test.add(Tokens.add());
        test.add(Tokens.value(4));

        final Transformer sut = new Transformer();
        final Queue<Token> result = sut.transform(test).getOrThrow();
        final List<Token> expected = List.of(Tokens.value(5.0), Tokens.value(4), Tokens.add());
        assertArrayEquals(expected.toArray(),result.toArray());
    }

    @Test
    void transformCalucaltion() {
        Deque<Token> test = new LinkedList<>();
        test.add(Tokens.value(3));
        test.add(Tokens.add());
        test.add(Tokens.value(4));
        test.add(Tokens.mul());
        test.add(Tokens.value(2));

        final Transformer sut = new Transformer();
        final Queue<Token> result = sut.transform(test).getOrThrow();
        final List<Token> expected = List.of(Tokens.value(3.0), Tokens.value(4), Tokens.value(2), Tokens.mul(), Tokens.add());
        assertArrayEquals(expected.toArray(),result.toArray());
    }
}