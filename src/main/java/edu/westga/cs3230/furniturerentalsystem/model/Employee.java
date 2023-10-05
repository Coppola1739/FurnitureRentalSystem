package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Employee {

    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Date hireDate;
    private String gender;
    private String phoneNumber;
    private Date birthday;
}
