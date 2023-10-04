package edu.westga.cs3230.furniturerentalsystem.model;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer {
    @NonNull
    private String customerId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String gender;
    private String phoneNumber;
    private Date birthday;
    @NonNull
    private Date registrationDate;
}
