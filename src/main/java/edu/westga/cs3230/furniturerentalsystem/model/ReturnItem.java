package edu.westga.cs3230.furniturerentalsystem.model;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
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
	
	 public void determineFineAmount(Date dueDate, double price) {
		 	Instant instantDueDate = Instant.ofEpochMilli(dueDate.getTime());
	        Instant currentInstant = Instant.now();

	        long daysBetween = Duration.between(instantDueDate, currentInstant).toDays();
	        if (daysBetween < 0) {
	        	daysBetween = 0;
	        }

	        this.fineAmount = price * daysBetween * this.quantity;
	    }
	
	public static ReturnItem changeRentalItemToReturnItem(RentalItem rentalItem, String returnId) {
		ReturnItem newReturnItem = new ReturnItem();
		newReturnItem.rentalId = rentalItem.getRentalId();
		newReturnItem.furnitureId = rentalItem.getFurnitureId();
		newReturnItem.returnId = returnId;
		newReturnItem.quantity = rentalItem.getQuantity();
		
		Rental rental = RentalDao.getRentalByRentalId(rentalItem.getRentalId());
		newReturnItem.determineFineAmount(rental.getDueDate(),rentalItem.getCost());
		return newReturnItem;
	}

}
