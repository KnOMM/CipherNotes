package com.example.hw04.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VigenereDto {
    @NotNull(message = "Text should not be empty")
    @Pattern(regexp = ".{1,250}", message = "Must be some text less than 250 bytes")
    private String pass;
    @NotEmpty(message = "Text should not be empty")
    @Size(min = 3, max = 250, message = "Text field must be in range 3-250 characters")
    private String text;
}
