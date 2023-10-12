package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer {
	@NonNull
	private String customerId;
	@NonNull
	private PersonalInformation pInfo;

	public Customer(String customerId, PersonalInformation pInfo)
			throws IllegalArgumentException {

		if (customerId == null || customerId.trim().isEmpty()) {
			throw new IllegalArgumentException("Customer ID cannot be null or empty");
		}
		if (pInfo == null) {
			throw new IllegalArgumentException("Personal informaiton is missing");
		}
		
		this.customerId = customerId;
		this.pInfo = pInfo;
	}

}
