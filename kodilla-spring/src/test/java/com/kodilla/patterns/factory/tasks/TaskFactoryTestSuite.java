package com.kodilla.patterns.factory.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskFactoryTestSuite {

    @Test
    void testFactoryShopping() {
      //When
        Task shopping = TaskFactory.makeTask(TaskFactory.SHOPPING, "Shopping", "Bread", null, 2.0);
        shopping.executeTask();

        //Then
        assertEquals("Shopping", shopping.getTaskName());
        assertTrue(shopping.isTaskExecuted());
    }

    @Test
    void testFactoryPainting() {
     //When
        Task painting = TaskFactory.makeTask(TaskFactory.PAINTING, "Painting", "Red", "Fence", 0);
        painting.executeTask();

     //Then
        assertEquals("Painting", painting.getTaskName());
        assertTrue(painting.isTaskExecuted());
    }

    @Test
    void testFactoryException() {
        assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.makeTask("UNKNOWN", "Test", "Test", "Test", 0);
        });
    }
}
