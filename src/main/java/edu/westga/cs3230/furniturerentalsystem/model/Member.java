package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

/**
 * Member model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member{
    @NonNull
    private String memberId;
    @NonNull
    private String pId;
    @NonNull
    private PersonalInformation pInfo;

    /**
     * To String method
     *
     * @return String the output string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Member ID: ").append(this.memberId).append(" ");
        output.append("Name: ").append(this.pInfo.getFirstName()).append(" ").append(this.pInfo.getLastName());
        return output.toString();
    }
}
