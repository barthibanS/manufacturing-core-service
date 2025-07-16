package org.sfs.dm.manufacturing_core_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonModel {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer yearOfBirth;
}