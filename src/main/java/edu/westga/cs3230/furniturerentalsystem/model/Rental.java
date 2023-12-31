package edu.westga.cs3230.furniturerentalsystem.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class Rental {

	@NonNull
	private String rentalId;

	@NonNull
	private String memberId;

	@NonNull
	private String employeeId;

	@NonNull
	private Date startDate;

	@NonNull
	private Date dueDate;

	@Override
	public String toString() {
		StringBuilder inlineString = new StringBuilder();
		inlineString.append(" Rental ID: ").append(this.rentalId);
		inlineString.append(" Start Date: ").append(this.startDate);
		inlineString.append(" Due Date: ").append(this.dueDate);
		inlineString.append(" Member ID: ").append(this.memberId);
		inlineString.append(" Employee ID:").append(this.employeeId);
		return inlineString.toString();
	}

}
