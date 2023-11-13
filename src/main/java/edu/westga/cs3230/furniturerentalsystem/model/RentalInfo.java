package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalInfo {

	@NonNull
	private String rentalId;

	@NonNull
	private String memberFullName;

	@NonNull
	private String memberId;

	@NonNull
	private String employeeFullName;

	@NonNull
	private String employeeId;

	@NonNull
	private Date startDate;

	@NonNull
	private Date dueDate;

	@NonNull
	private List<RentalItem> rentalItems;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("------- Rental Receipt -------\n");
		sb.append("Rental ID: ").append(this.rentalId).append("\n");
		sb.append("Member: ").append(this.memberFullName).append(" (ID: ").append(this.memberId).append(")\n");
		sb.append("Employee: ").append(this.employeeFullName).append(" (ID: ").append(this.employeeId).append(")\n");
		sb.append("Start Date: ").append(this.startDate).append("\n");
		sb.append("Due Date: ").append(this.dueDate).append("\n");
		sb.append("Items Rented:\n");

		for (RentalItem item : this.rentalItems) {
			sb.append(item.toString()).append("\n");
		}

		sb.append("-------------------------------");

		return sb.toString();
	}

}
