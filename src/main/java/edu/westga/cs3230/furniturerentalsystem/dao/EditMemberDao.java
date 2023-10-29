package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.Date;
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

	public void updateMember(String column, String value, String pId) {
		String updateMemberCity = "UPDATE personal_information " + "SET " + column + " = ? "
				+ "WHERE personal_information.pid = ?";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement updateStmt = connection.prepareStatement(updateMemberCity)) {
			updateStmt.setString(1, value);
			updateStmt.setString(2, pId);

			int rowsUpdated = updateStmt.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println(rowsUpdated + " rows updated successfully.");
			} else {
				System.out.println("No rows were updated.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void updateBirthday(LocalDate newBirthday, String pId) {
		String updateBirthday = "UPDATE personal_information " + "SET b_date = ? " + "WHERE pid = ?";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement updateStmt = connection.prepareStatement(updateBirthday)) {
			Date sqlDate = Date.valueOf(newBirthday); 
			updateStmt.setDate(1, sqlDate); // Convert Java Date to SQL Date
	        updateStmt.setString(2, pId);

			int rowsUpdated = updateStmt.executeUpdate();

			if (rowsUpdated > 0) {
				System.out.println(rowsUpdated + " rows updated successfully.");
			} else {
				System.out.println("No rows were updated.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
