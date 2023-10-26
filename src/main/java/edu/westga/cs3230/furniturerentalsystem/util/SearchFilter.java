package edu.westga.cs3230.furniturerentalsystem.util;

import lombok.Getter;

/**
 * Search filter enum
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
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
