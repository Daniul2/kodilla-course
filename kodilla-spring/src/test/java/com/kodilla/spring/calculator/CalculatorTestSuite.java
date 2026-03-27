package com.kodilla.spring.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class CalculatorTestSuite {

    @Test
    void testCalculations() {
        //Given
        ApplicationContext context = new AnnotationConfigApplicationContext("com.kodilla.spring");
        Calculator calculator = context.getBean(Calculator.class);

        //When
        double addResult = calculator.add(10, 5);
        double subResult = calculator.sub(10, 5);
        double mulResult = calculator.mul(10, 5);
        double divResult = calculator.div(10, 5);

        //Then
        Assertions.assertEquals(15.0, addResult);
        Assertions.assertEquals(5.0, subResult);
        Assertions.assertEquals(50.0, mulResult);
        Assertions.assertEquals(2.0, divResult);
    }

    @Test
    void testDivisionByZero() {
        //Given
        ApplicationContext context = new AnnotationConfigApplicationContext("com.kodilla.spring");
        Calculator calculator = context.getBean(Calculator.class);

        //When
        double result = calculator.div(10, 0);

        //Then
        Assertions.assertTrue(Double.isInfinite(result));
    }
}
