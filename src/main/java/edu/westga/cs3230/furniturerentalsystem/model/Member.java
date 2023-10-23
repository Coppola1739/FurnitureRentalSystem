package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member {
    @NonNull
    private String member_id;
    @NonNull
    private String pId;
    @NonNull
    private PersonalInformation pInfo;

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Member ID: ").append(this.member_id).append(" ");
        output.append("Name: ").append(pInfo.getFirstName()).append(" ").append(pInfo.getLastName());
        return output.toString();
    }

}
