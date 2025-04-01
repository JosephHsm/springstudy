package com.example.demo1.datalogic;

import org.springframework.web.bind.annotation.*;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping("/api/todo/{userId}")
    public Todo createTodo(@PathVariable Long userId, @RequestBody Todo todo){
        return todoService.createTodo(todo, userId);
    }
}
