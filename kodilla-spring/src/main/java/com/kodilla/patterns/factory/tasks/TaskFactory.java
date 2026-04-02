package com.kodilla.patterns.factory.tasks;

public final class TaskFactory {
    public static final String SHOPPING = "SHOPPING";
    public static final String PAINTING = "PAINTING";
    public static final String DRIVING = "DRIVING";


    public static Task makeTask(final String taskType, final String taskName,
                                final String param1, final String param2, final double value) {
        return switch (taskType) {
            case SHOPPING -> new ShoppingTask(taskName, param1, value);
            case PAINTING -> new PaintingTask(taskName, param1, param2);
            case DRIVING -> new DrivingTask(taskName, param1, param2);
            default -> throw new IllegalArgumentException("Unknown task type: " + taskType);
        };
    }
}
