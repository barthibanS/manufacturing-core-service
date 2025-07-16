package org.sfs.dm.manufacturing_core_service.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonModel {
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50, message = "first name must be between 4 and 50 characters")
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 50, message = "last name must be between 4 and 50 characters")
    private String lastName;

    @NotNull
    @Max(value = 2026, message = "year of birth cannot be in the future")
    private Integer yearOfBirth;
}