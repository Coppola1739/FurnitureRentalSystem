package edu.westga.cs3230.furniturerentalsystem.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Return {

	String returnId;
	String memberId;
	String employeeId;
	
	
	@Override
    public String toString() {
        return "Return " + returnId;
	}
	
	public static String generateReturnReceipt(ArrayList<ReturnItem> returnItemList) {
        StringBuilder receipt = new StringBuilder();

        double totalFine = 0.0;
        for (ReturnItem returnItem : returnItemList) {
            receipt.append("Rental ID: ").append(returnItem.getRentalId()).append("\n");
            receipt.append("Furniture ID: ").append(returnItem.getFurnitureId()).append("\n");
            receipt.append("Quantity: ").append(returnItem.getQuantity()).append("\n");
            receipt.append("Fine Amount: ").append(returnItem.getFineAmount()).append("\n");
            receipt.append("----------------------------\n");

            totalFine += returnItem.getFineAmount();
        }
        String formattedTotalFine = new DecimalFormat("#.##").format(totalFine);

        receipt.append("Total Fine: ").append(formattedTotalFine);

        return receipt.toString();
    }
}
