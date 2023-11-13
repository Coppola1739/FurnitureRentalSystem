package edu.westga.cs3230.furniturerentalsystem.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter	
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnItem {
	
	private String rentalId;
	private String furnitureId;
	private String returnId;
	private double fineAmount;
	private int quantity;
	
	public void determineFineAmount(Date dueDate, Date returnDate, double price) {
        LocalDate localDate = returnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(localDate, currentDate);
        
        this.fineAmount = price * daysBetween * this.quantity;
    }
	
	public ReturnItem changeRentalItemToReturnItem(RentalItem rentalItem, String returnId) {
		ReturnItem newReturnItem = new ReturnItem();
		newReturnItem.rentalId = rentalItem.getRentalId();
		newReturnItem.furnitureId = rentalItem.getFurnitureId();
		newReturnItem.returnId = returnId;
		newReturnItem.quantity = rentalItem.getQuantity();
		newReturnItem.fineAmount = 0.00;
		return newReturnItem;
	}

	

}
