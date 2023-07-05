package org.example;

import org.example.calc.Calculator;
import org.example.calc.Calculators;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Calculator calc = Calculators.calculatorWithoutPrecedence();
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print("Calculate: ");
            String str = s.nextLine();
            if (Objects.equals(str, "exit")) {
                System.exit(0);
            }
            final Result<BigDecimal, String> result = calc.calculate(str);
            System.out.println(result.map(BigDecimal::toString).map(x -> "Result: " + x).getOrElse("Error"));
        }
    }
}