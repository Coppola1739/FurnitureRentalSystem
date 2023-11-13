package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

/**
 * Style model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Style {
    @NonNull
    private String name;
}
