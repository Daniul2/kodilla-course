package com.kodilla.patterns.singleton;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerTestSuite {
    private static Logger logger;

    @BeforeAll
    public static void setUp() {
        logger = Logger.INSTANCE;
    }

    @Test
    public void testGetLastLog() {
        // Given
        String logMessage = "Log for test 1";

        // When
        logger.log(logMessage);
        String result = logger.getLastLog();

        // Then
        assertEquals("Log for test 1", result);
    }

    @Test
    public void testNewLog() {
        // Given
        String logMessage = "User login: admin";

        // When
        logger.log(logMessage);
        String result = logger.getLastLog();

        // Then
        assertEquals("User login: admin", result);
    }
}
