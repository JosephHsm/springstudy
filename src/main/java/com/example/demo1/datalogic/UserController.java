package com.example.demo1.datalogic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String whenGet() {
        return "GET요청을 받았음";
    }

    @PostMapping
    public String whenPost(User user) {
        userService.createUser(user);
        return "유저 POST 성공";
    }

    @DeleteMapping
    public String whenDelete (){
        return "DELETE요청을 받았음";
    }

    @PutMapping
    public String whenPUT (){
        return "PUT요청을 받았음";
    }
}
