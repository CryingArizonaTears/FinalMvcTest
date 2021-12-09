package com.senla.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApiException {
    private String name;
    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;
}
