package com.assistant.dto.response;

import com.assistant.dto.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponse {

    private boolean success;
    private String message;
    private Integer code;
    private Company data;

}
