package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

/**
 * Furniture model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
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
    
    public Furniture(String furnitureId, String styleName, String categoryName, String rentalRate, int quantity) {
    	if (quantity < 0){
    		throw new IllegalArgumentException("Quantity must be greater than zero");
    	}
    	this.furnitureId = furnitureId;
    	this.styleName = styleName;
    	this.categoryName = categoryName;
    	this.rentalRate = rentalRate;
    	this.quantity = quantity;
    }
    
    @Override
    public String toString() {
    	StringBuilder output = new StringBuilder();
    	output.append("Furniture ID: ").append(this.furnitureId);
    	output.append(" Style: ").append(this.styleName);
    	output.append(" Category: ").append(this.categoryName);
    	output.append(" Rental Rate: ").append(this.rentalRate);
    	return output.toString();
    }
    
}
