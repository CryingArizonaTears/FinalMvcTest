package com.senla.model.dto.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatFilter {
    private Long id;
    private String name;
    private Long userProfileId;
}
