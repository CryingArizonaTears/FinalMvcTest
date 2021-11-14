package com.senla.controllers;

import com.senla.api.dao.IUserProfileDao;
import com.senla.api.service.IUserService;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserLoginDto;
import com.senla.model.dto.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileDao userProfileDao;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> userProfileDto(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

//    @GetMapping("/test")
//    public void test() {
//        UserLoginDto userLoginDto = new UserLoginDto();
//        userLoginDto.setUsername("qwe");
//        userLoginDto.setPassword("1234");
//        userService.registration(userLoginDto);
//    }

    @PostMapping("/registration")
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        userService.registration(userDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/editLogin")
    public ResponseEntity<Void> updateUserLogin(@PathVariable Long id, @RequestBody UserLoginDto userLoginDto ){
        System.out.println(userLoginDto);
        userLoginDto.setId(id);
        userService.editLogin(userLoginDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/editProfile")
    public ResponseEntity<Void> updateUserProfile(@PathVariable Long id, @RequestBody UserProfileDto userProfileDto ){
        userService.editProfile(id, userProfileDto);
        return ResponseEntity.noContent().build();
    }
}
