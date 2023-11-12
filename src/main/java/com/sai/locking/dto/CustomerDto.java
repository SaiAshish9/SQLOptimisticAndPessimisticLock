package com.sai.locking.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String firstName;
    private String lastName;
    private Integer version;
}
