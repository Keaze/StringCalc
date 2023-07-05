package org.example.calc;

import org.example.Result;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    static Calculator calc = new Calculator();

    private static Stream<Arguments> provideErrors() {
        return Stream.of(
                Arguments.of("3 ++ 2"),
                Arguments.of("3 +- 2"),
                Arguments.of("1 2"),
                Arguments.of("2 +a")
        );
    }

    private static Stream<Arguments> proviceCalculations() {
        return Stream.of(
                Arguments.of("5+5", 10.0),
                Arguments.of("42", 42),
                Arguments.of("2 + 3", 5),
                Arguments.of("1+2+3", 6),
                Arguments.of("3-2", 1),
                Arguments.of("2 - 3", -1),
                Arguments.of("2+3-2", 3),
                Arguments.of("3-2+1", 2),
                Arguments.of("5-3-2", 0),
                Arguments.of("3+2-2+3", 6),
                Arguments.of("3*2", 6),
                Arguments.of("0.5+0.75", 1.25),
                Arguments.of("3*2*2", 12),
                Arguments.of("4/2", 2),
                Arguments.of("3/2", 1.5),
                Arguments.of("3 + 2 * 5", 25),
                Arguments.of("3 + 2 * 5", 25));
    }

    @ParameterizedTest
    @MethodSource("provideErrors")
    void erros(String input) {
        final Result<BigDecimal, String> result = calc.calculate(input);
        assert !result.successful();
    }

    @ParameterizedTest
    @MethodSource("proviceCalculations")
    void calculations(String input, double expected) {
        final Result<BigDecimal, String> result = calc.calculate(input);
        assertEquals(Result.success(expected), result.map(BigDecimal::doubleValue));
    }
}