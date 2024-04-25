package com.maorzehavi.taskmanager.controller;

import com.maorzehavi.taskmanager.model.TaskDto;
import com.maorzehavi.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        try {
            return ResponseEntity.ok(taskService.getAllTasks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/complete")
    public ResponseEntity<?> getCompletedTasks() {
        try {
            return ResponseEntity.ok(taskService.getCompletedTasks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/incomplete")
    public ResponseEntity<?> getIncompleteTasks() {
        try {
            return ResponseEntity.ok(taskService.getIncompleteTasks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskService.getTask(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDto taskDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        System.out.println("TaskDto: " + taskDto);
        URI location = URI.create("/task/" + taskDto.getId());
        try {
            return ResponseEntity.created(location).body(taskService.createTask(taskDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        try {
            return ResponseEntity.ok(taskService.updateTask(id, taskDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskService.completeTask(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}/incomplete")
    public ResponseEntity<?> incompleteTask(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskService.incompleteTask(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
