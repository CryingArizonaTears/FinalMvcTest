package com.senla.controllers;

import com.senla.api.service.IUserService;
import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserLoginDto;
import com.senla.model.dto.UserProfileDto;
import com.senla.model.dto.filter.AdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService userService;


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
        AdFilter adFilter = new AdFilter();
        adFilter.setUserId(id);
        adFilter.setStatus("CLOSED");
        return ResponseEntity.ok(userService.salesHistory(adFilter));
    }
}
