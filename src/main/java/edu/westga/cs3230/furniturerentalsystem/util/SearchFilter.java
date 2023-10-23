package edu.westga.cs3230.furniturerentalsystem.util;

import lombok.Getter;

@Getter
public enum SearchFilter {
    PHONE_NUMBER("Phone Number"),
    MEMBER_ID("Member ID"),
    NAME("Name");

    private final String displayName;

    SearchFilter(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
