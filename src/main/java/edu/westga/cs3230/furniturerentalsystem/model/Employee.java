package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Employee {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private Date birthday;
    private Date hireDate;
}
