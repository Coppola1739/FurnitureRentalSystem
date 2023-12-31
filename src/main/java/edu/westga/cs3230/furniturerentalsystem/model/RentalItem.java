package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter	
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalItem {

	private String rentalId;
	@NonNull
	private String furnitureId;

	private int quantity;

	private double cost;

	@Override
	public String toString() {
		return "Item ID: " + this.furnitureId + ", Quantity: " + this.quantity + ", Cost: $" + this.cost;
	}
	
}
