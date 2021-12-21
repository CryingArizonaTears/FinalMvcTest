package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private String username;
    @ToString.Exclude
    private String password;
    private String fullName;
}
