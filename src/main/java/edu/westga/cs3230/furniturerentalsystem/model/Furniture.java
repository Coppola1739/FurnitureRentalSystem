package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

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
}
