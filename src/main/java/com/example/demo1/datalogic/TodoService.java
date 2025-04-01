package com.example.demo1.datalogic;

import org.springframework.stereotype.Service;

import java.util.List;

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

    // Todo 조회 메서드 (특정 유저의 Todo 리스트 반환)
    public List<Todo> getTodosByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return todoRepository.findAllByUser(user);
    }
    // Todo 수정 메서드
    public Todo updateTodo(Todo todo, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 요청으로 받은 Todo id로 기존 Todo 조회
        Todo existingTodo = todoRepository.findById(todo.getId())
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // 수정 요청한 userId와 기존 Todo의 소유자 일치 여부 확인
        if (existingTodo.getUser().getId() != user.getId()) {
            throw new RuntimeException("Todo does not belong to the user");
        }

        // 필요한 필드 업데이트 (예: title, todo 내용)
        existingTodo.setTitle(todo.getTitle());
        existingTodo.setTodo(todo.getTodo());

        return todoRepository.save(existingTodo);
    }
    // Todo 삭제 메서드
    public void deleteTodo(Long todoId) {
        // 존재 여부 확인 후 삭제 가능 (필요에 따라 검증 로직 추가 가능)
        todoRepository.deleteById(todoId);
    }
}
