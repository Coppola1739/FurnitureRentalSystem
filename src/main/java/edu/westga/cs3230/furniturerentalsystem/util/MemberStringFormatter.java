package edu.westga.cs3230.furniturerentalsystem.util;

import edu.westga.cs3230.furniturerentalsystem.model.Member;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberStringFormatter {

    public String formatMemberString(Member member) {
        StringBuilder output = new StringBuilder();
        output.append("Member ID: ").append(member.getMemberId()).append("\n");
        output.append("Personal Information:\n");
        output.append("   - First Name: ").append(member.getPInfo().getFirstName()).append("\n");
        output.append("   - Last Name: ").append(member.getPInfo().getLastName()).append("\n");
        output.append("   - Registration Date: ").append(member.getPInfo().getRegistrationDate()).append("\n");
        output.append("   - Gender: ").append(member.getPInfo().getGender()).append("\n");
        output.append("   - Phone Number: ").append(member.getPInfo().getPhoneNumber()).append("\n");
        output.append("   - Birthday: ").append(member.getPInfo().getBirthday()).append("\n");
        output.append("   - Address: ").append(member.getPInfo().getAddress()).append("\n");
        output.append("   - City: ").append(member.getPInfo().getCity()).append("\n");
        output.append("   - State: ").append(member.getPInfo().getState()).append("\n");
        output.append("   - ZIP: ").append(member.getPInfo().getZip()).append("\n");
        return output.toString();
    }
}
