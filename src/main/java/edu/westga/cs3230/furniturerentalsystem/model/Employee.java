package edu.westga.cs3230.furniturerentalsystem.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Employee model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Employee{

    @NonNull
    private String employeeNum;
    @NonNull
    private String pId;
    @NonNull
    private String username;
    private PersonalInformation pInfo;


    /**
     * To String method
     *
     * @return String the output string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Employee number: ").append(this.employeeNum).append(" ");
        output.append("Name: ").append(this.pInfo.getFirstName()).append(" ").append(this.pInfo.getLastName());
        return output.toString();
    }
}
