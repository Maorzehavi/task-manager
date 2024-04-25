package com.maorzehavi.taskmanager.service;

import com.maorzehavi.taskmanager.model.Task;
import com.maorzehavi.taskmanager.model.TaskDto;
import com.maorzehavi.taskmanager.repositpry.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskDto createTask(TaskDto taskDto) {
        Task task = mapToTask(taskDto);
        Task savedTask = taskRepository.save(task);
        return mapToTaskDto(savedTask);
    }

    public TaskDto getTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        return mapToTaskDto(task);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        if (taskDto.getCompleted() == null) {
            taskDto.setCompleted(false);
        }
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setTitle(taskDto.getTitle());
        task.setCompleted(taskDto.getCompleted());
        Task savedTask = taskRepository.save(task);
        return mapToTaskDto(savedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }


    public TaskDto completeTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setCompleted(true);
        Task savedTask = taskRepository.save(task);
        return mapToTaskDto(savedTask);
    }
    public TaskDto incompleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setCompleted(false);
        Task savedTask = taskRepository.save(task);
        return mapToTaskDto(savedTask);
    }

    public Task mapToTask(TaskDto taskDto) {
        if (taskDto.getCompleted() == null) {
            taskDto.setCompleted(false);
        }
        return Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .completed(taskDto.getCompleted())
                .build();
    }

    public TaskDto mapToTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .completed(task.getCompleted())
                .build();
    }

    public List<TaskDto> getCompletedTasks() {
        return taskRepository.findByCompleted(true).stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getIncompleteTasks() {
        return taskRepository.findByCompleted(false).stream()
                .map(this::mapToTaskDto)
                .collect(Collectors.toList());
    }
}
