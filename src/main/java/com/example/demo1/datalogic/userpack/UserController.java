package com.example.demo1.datalogic.userpack;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 생성
    // 유저 생성
    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);

        // 생성 시에는 updatedAt을 굳이 보여주지 않는 DTO로 변환
        UserCreateResponse response = new UserCreateResponse(
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getEmail(),
                createdUser.getCreatedAt()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 유저 목록 조회
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 유저 상세 조회
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 유저 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @PathVariable("userId") Long id,
            @RequestBody User updatedUser
    ) {
        User user = userService.updateUser(id, updatedUser);

        // 수정 시에는 updatedAt까지 포함하는 DTO로 변환
        UserUpdateResponse response = new UserUpdateResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    // 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
