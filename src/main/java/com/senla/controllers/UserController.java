package com.senla.controllers;

import com.senla.api.service.IAdService;
import com.senla.api.service.IUserService;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserLoginDto;
import com.senla.model.dto.UserProfileDto;
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
    private IAdService adService;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        userService.registration(userDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/editLogin")
    public ResponseEntity<Void> updateUserLogin(@PathVariable Long id, @RequestBody UserLoginDto userLoginDto) {
        userLoginDto.setId(id);
        userService.editLogin(userLoginDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/editProfile")
    public ResponseEntity<Void> updateUserProfile(@PathVariable Long id, @RequestBody UserProfileDto userProfileDto) {
        userService.editProfile(id, userProfileDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/salesHistory")
    public ResponseEntity<List<AdDto>> getUserSalesById(@PathVariable Long id) {
        return ResponseEntity.ok(adService.filterClosedByUserId(id));
    }
}
