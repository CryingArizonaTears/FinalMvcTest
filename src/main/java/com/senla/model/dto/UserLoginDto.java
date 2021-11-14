package com.senla.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginDto {

    private Long id;
    private String username;
    private String password;

}
