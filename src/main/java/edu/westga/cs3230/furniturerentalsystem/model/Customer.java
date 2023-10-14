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
}
