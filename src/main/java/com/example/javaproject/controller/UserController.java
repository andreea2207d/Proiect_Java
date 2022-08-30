package com.example.javaproject.controller;

import com.example.javaproject.exception.EmailAlreadyRegistered;
import com.example.javaproject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.javaproject.dto.UserDto;
import com.example.javaproject.service.UserService;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/shop/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) throws EmailAlreadyRegistered, NoSuchAlgorithmException {
        return ResponseEntity
                .ok()
                .body(userService.registerUser(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(userService.findUserById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity
                .ok()
                .body(userService.findAllUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long id) throws NoSuchAlgorithmException {
        return ResponseEntity
                .ok()
                .body(userService.updateUser(userMapper.mapToEntity(userDto), id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
