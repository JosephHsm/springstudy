package com.example.demo1.datalogic;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // 생성자에서 두 repository 모두 주입해야 합니다
    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    // Todo 생성 메서드
    public Todo createTodo(Todo todo, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        todo.setUser(user); // Todo 객체에 유저 설정
        return todoRepository.save(todo); // 저장 후 반환
    }
}
