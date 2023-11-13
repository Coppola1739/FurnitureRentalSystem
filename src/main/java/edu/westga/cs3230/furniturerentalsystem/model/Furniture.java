package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

/**
 * Furniture model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Furniture {

    @NonNull
    private String furnitureId;
    @NonNull
    private String styleName;
    @NonNull
    private String categoryName;
    @NonNull
    private String rentalRate;
    
    private int quantity;
    
    @Override
    public String toString() {
    	StringBuilder output = new StringBuilder();
    	output.append("Furniture ID: ").append(this.furnitureId);
    	output.append(" Style: ").append(this.styleName);
    	output.append(" Category: ").append(this.categoryName);
    	output.append(" Rental Rate: ").append(this.rentalRate);
    	output.append(" Quantity:").append(this.quantity);
    	return output.toString();
    }
    
}
