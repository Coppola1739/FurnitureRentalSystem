package edu.westga.cs3230.furniturerentalsystem.util;

import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import lombok.NoArgsConstructor;

/**
 * Formatter for member string
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
@NoArgsConstructor
public class EmployeeStringFormatter {
    /**
     * Creates an output string for a member
     *
     * @param employee the member to format
     * @return String the output
     */
    public String formatEmployeeString(Employee employee) {
        StringBuilder output = new StringBuilder();
        output.append("Employee Number: ").append(employee.getEmployeeNum()).append("\n");
        output.append("Personal Information:\n");
        output.append("   - First Name: ").append(employee.getPInfo().getFirstName()).append("\n");
        output.append("   - Last Name: ").append(employee.getPInfo().getLastName()).append("\n");
        output.append("   - Registration Date: ").append(employee.getPInfo().getRegistrationDate()).append("\n");
        output.append("   - Gender: ").append(employee.getPInfo().getGender()).append("\n");
        output.append("   - Phone Number: ").append(employee.getPInfo().getPhoneNumber()).append("\n");
        output.append("   - Birthday: ").append(employee.getPInfo().getBirthday()).append("\n");
        output.append("   - Address: ").append(employee.getPInfo().getAddress()).append("\n");
        output.append("   - City: ").append(employee.getPInfo().getCity()).append("\n");
        output.append("   - State: ").append(employee.getPInfo().getState()).append("\n");
        output.append("   - ZIP: ").append(employee.getPInfo().getZip()).append("\n");
        return output.toString();
    }
}
