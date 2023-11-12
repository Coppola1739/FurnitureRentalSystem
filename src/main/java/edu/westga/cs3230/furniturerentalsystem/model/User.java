package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.Data;
import lombok.NonNull;
@Data
public abstract class User {
    private String pId;
    private PersonalInformation pInfo;

}
