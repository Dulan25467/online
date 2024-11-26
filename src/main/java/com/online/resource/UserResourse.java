package com.online.resource;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResourse {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @Min(value = 10000000, message = "Phone number must be at least 8 digits")
    @Max(value = 999999999999L, message = "Phone number cannot exceed 12 digits")
    private long phone;

    @NotBlank(message = "Address is required")
    private String address;
}
