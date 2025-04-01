package com.example.demo1.datalogic;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    // Todo 조회 엔드포인트 (특정 유저의 Todo 리스트 반환)
    @GetMapping("/api/todo/{userId}")
    public List<Todo> getTodosByUser(@PathVariable Long userId) {
        return todoService.getTodosByUser(userId);
    }

    // Todo 수정 엔드포인트
    @PutMapping("/api/todo/{userId}")
    public Todo updateTodo(@PathVariable Long userId, @RequestBody Todo todo) {
        return todoService.updateTodo(todo, userId);
    }

    // Todo 삭제 엔드포인트
    @DeleteMapping("/api/todo/{todoId}")
    public void deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
    }
}
