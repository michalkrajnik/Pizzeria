package com.sda.pizzeria.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserRequest {

    private Long appUserId;
    private String editedUsername;


}
