package com.senla.controllers;

import com.senla.model.dto.UserProfileDto;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> UserProfileDto(@PathVariable Long id){
        return ResponseEntity.ok(userService.getById(id));
    }


    @GetMapping("/test")
    public String UserProfileDto1(){
        return "test";
    }
}
