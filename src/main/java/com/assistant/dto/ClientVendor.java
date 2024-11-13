package com.assistant.dto;

import com.assistant.enums.ClientVendorType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientVendor {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;

    private String phone;

    private String website;

    private String email;

    private ClientVendorType clientVendorType;

    private Address address;

    @JsonIgnore
    private Company company;

    @JsonIgnore
    private boolean hasInvoice;

}
