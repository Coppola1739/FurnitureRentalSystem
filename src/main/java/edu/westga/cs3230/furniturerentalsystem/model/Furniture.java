package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    
//    @Override
//    public String toString() {
//    	StringBuilder output = new Stringbuilder();
//    	output.append(false)
//    }
}
