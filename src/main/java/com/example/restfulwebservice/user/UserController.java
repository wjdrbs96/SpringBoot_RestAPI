package com.example.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    // GET /users/1 or /users/10 -> 뒤에 붙은 숫자는 int 가 아니라 String 형태임
    // String 이어도 Converter 되기 때문에 int id 로 매게변수로 받아도 됨
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return userDaoService.findOne(id);
    }

    // 매게 변수로 Object 타입의 형태로 받기 위해서는 @RequestBody 어노테이션이 필요함
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createUser = userDaoService.addUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}