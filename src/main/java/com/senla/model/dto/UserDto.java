package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserDto {

    private String username;
    private String password;
    private String fullName;

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
