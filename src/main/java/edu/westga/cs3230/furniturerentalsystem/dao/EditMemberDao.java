package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;

public class EditMemberDao {

	private Member currMember;

	public EditMemberDao(Member member) {
		this.currMember = member;
	}

	public void updateMember(String column, String value, String memberId) {
		String updateMemberCity = "UPDATE personal_information " +
                "SET " + column + " = ? " +
                "WHERE personal_information.pid = ?";

try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
PreparedStatement updateStmt = connection.prepareStatement(updateMemberCity)) {
updateStmt.setString(1, value);
updateStmt.setString(2, memberId);

int rowsUpdated = updateStmt.executeUpdate();

if (rowsUpdated > 0) {
System.out.println(rowsUpdated + " rows updated successfully.");
} else {
System.out.println("No rows were updated.");
}
} catch (SQLException e) {
e.printStackTrace();
// Handle any SQLException, such as database connection errors.
}
}
	

	public void updateCity(String text) {
		
	}

	public void updateFirstName(String field, String text) {
		String updateMemberCity = "UPDATE personal_information SET ? = ? " + "FROM member "
				+ "WHERE personal_information.pid = " + this.currMember.getMemberId();
		
		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement updateStmt = connection.prepareStatement(updateMemberCity)) {
			updateStmt.setString(1, field);
			updateStmt.setString(2, text);
			

			int rowsUpdated = updateStmt.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println(rowsUpdated + " rows updated successfully.");
			} else {
				System.out.println("No rows were updated.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle any SQLException, such as database connection errors.
		}
	}

	public void updateGender(String value) {
		// TODO Auto-generated method stub

	}

	public void updateLastName(String text) {
		// TODO Auto-generated method stub

	}

	public void updatePhoneNumber(String text) {
		// TODO Auto-generated method stub

	}

	public void updateStreetAddress(String text) {
		// TODO Auto-generated method stub

	}

	public void updateState(String value) {
		// TODO Auto-generated method stub

	}

	public void updateBirthday(LocalDate value) {
		// TODO Auto-generated method stub

	}
}
