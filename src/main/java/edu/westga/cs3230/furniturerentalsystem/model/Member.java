package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member {
    @NonNull
    private String memberId;
    @NonNull
    private String pId;
    @NonNull
    private PersonalInformation pInfo;

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Member ID: ").append(this.memberId).append(" ");
        output.append("Name: ").append(pInfo.getFirstName()).append(" ").append(pInfo.getLastName());
        return output.toString();
    }

}
