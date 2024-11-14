package com.assistant.dto.response;

import com.assistant.dto.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private boolean success;
    private String message;
    private Integer code;
    private User data;

}
