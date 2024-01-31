package com.example.encypher.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaesarDto {
    @NotNull(message = "Text should not be empty")
//    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Choose any digit")
    @Min(value = 1, message = "Password must be a positive number")
    @Pattern(regexp = "\\d{1,9}", message = "Password must be a valid number - {1,9} digits")
    private String pass;
    @NotEmpty(message = "Text should not be empty")
    @Size(min = 3, max = 250, message = "Text field must be in range 3-250 characters")
    private String text;
}
