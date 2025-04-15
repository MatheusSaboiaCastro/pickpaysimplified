package com.pickpaysimplified.dtos;

import java.math.BigDecimal;

import com.pickpaysimplified.domain.user.UserType;

public record UserDTO(String fistName, String lastName, String document, String email, String password,
        BigDecimal balance, UserType userType) {

}
