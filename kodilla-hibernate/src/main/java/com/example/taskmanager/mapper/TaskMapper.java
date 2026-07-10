package com.example.taskmanager.mapper;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.dto.TaskDto;
import com.kodilla.hibernate.task.dao.TaskDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {
    public Task mapToTask(TaskDto dto){
        return new Task(dto.id(), dto.name(), dto.description());
    }
    public TaskDto mapToTaskDto(Task task){
        return new TaskDto(task.getId(),task.getName(),task.getDescription());
    }
    public List<TaskDto>mapToTaskDtoList(List<Task>tasks){
        return tasks.stream()
                .map(this::mapToTaskDto)
                .toList();
    }
}
