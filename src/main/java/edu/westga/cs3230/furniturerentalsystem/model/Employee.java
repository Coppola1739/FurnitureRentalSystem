package edu.westga.cs3230.furniturerentalsystem.model;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @NonNull
    private String gender;
    @NonNull
    private String phoneNumber;
    @NonNull
    private Date birthday;

}
