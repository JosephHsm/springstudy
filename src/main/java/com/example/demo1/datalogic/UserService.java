package com.example.demo1.datalogic;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // 유저 생성
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 유저 조회
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    // 유저 수정
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setGender(updatedUser.getGender());
                    user.setAge(updatedUser.getAge());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
