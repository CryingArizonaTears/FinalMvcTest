package com.senla.controllers;

import com.senla.api.service.IRatingService;
import com.senla.api.service.IUserService;
import com.senla.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRatingService ratingService;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        userService.registration(userDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/login")
    public ResponseEntity<Void> updateUserLogin(@RequestBody UserLoginDto userLoginDto) {
        userService.editLogin(userLoginDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
        userService.editProfile(userProfileDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/salesHistory")
    public ResponseEntity<List<AdDto>> getUserSalesById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.salesHistory(id));
    }

    @PostMapping("/mark")
    public ResponseEntity<Void> addMarkToUser(@RequestBody RatingDto ratingDto) {
        ratingService.addMarkToUser(ratingDto);
        return ResponseEntity.noContent().build();
    }
}
