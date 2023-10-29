package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EditMemberDao {

	private static final String UPDATE_PERSONAL_INFORMATION = "UPDATE personal_information ";

	public boolean updateMember(String column, String value, String pId) {
		String updateMemberCity = UPDATE_PERSONAL_INFORMATION + "SET " + column + " = ? "
				+ "WHERE personal_information.pid = ?";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement updateStmt = connection.prepareStatement(updateMemberCity)) {
			updateStmt.setString(1, value);
			updateStmt.setString(2, pId);

			int rowsUpdated = updateStmt.executeUpdate();

			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateBirthday(LocalDate newBirthday, String pId) {
		String updateBirthday = UPDATE_PERSONAL_INFORMATION + "SET b_date = ? " + "WHERE pid = ?";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement updateStmt = connection.prepareStatement(updateBirthday)) {
			Date sqlDate = Date.valueOf(newBirthday);
			updateStmt.setDate(1, sqlDate);
			updateStmt.setString(2, pId);

			int rowsUpdated = updateStmt.executeUpdate();

			if (rowsUpdated > 0) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
