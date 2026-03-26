package com.kodilla.spring.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculatorTestSuite {
    @Autowired
    private Calculator calculator;

    @Test
    void testCalculations(){
        //Given
        double a = 10.0;
        double b = 5.0;

        //When
        double sum = calculator.add(a, b);
        double difference = calculator.sub(a, b);
        double product = calculator.mul(a, b);
        double quotient = calculator.div(a, b);

        //Then
        assertEquals(15.0, sum);
        assertEquals(5.0, difference);
        assertEquals(50.0, product);
        assertEquals(2.0, quotient);
    }
}
