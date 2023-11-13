package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

import java.util.Date;

/**
 * Personal Information model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonalInformation {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Date registrationDate;
    @NonNull
    private String gender;
    @NonNull
    private String phoneNumber;
    @NonNull
    private Date birthday;
    @NonNull
    private String address;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String zip;
    
}
