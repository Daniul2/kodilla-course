package com.kodilla.patterns.factory.tasks;

public final class PaintingTask extends AbstractTask {
    private final String color;
    private final String whatToPaint;

    public PaintingTask(String taskName, String color, String whatToPaint) {
        super(taskName);
        this.color = color;
        this.whatToPaint = whatToPaint;
    }

    @Override
    public void executeTask() {
        System.out.println("Executing task: " + taskName + " - painting " + whatToPaint + " in " + color);
        isExecuted = true;
    }
}
