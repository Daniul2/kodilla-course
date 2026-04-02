package com.kodilla.patterns.factory.tasks;

public abstract class AbstractTask implements Task{
    protected final String taskName;
    protected boolean isExecuted = false;

    protected AbstractTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public boolean isTaskExecuted() {
        return isExecuted;
    }
}

