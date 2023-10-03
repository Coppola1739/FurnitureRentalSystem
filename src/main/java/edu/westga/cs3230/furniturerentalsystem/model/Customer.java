package edu.westga.cs3230.furniturerentalsystem.model;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer {

    private String customerId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private Date birthday;
    private Date registrationDate;
}
