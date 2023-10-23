package edu.westga.cs3230.furniturerentalsystem.util;

public enum SearchFilter {
    PHONE_NUMBER("Phone Number"),
    MEMBER_ID("Member ID"),
    NAME("Name");

    private final String displayName;

    SearchFilter(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
