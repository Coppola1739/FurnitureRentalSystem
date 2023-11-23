package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.model.RentalItem;
import edu.westga.cs3230.furniturerentalsystem.model.Return;
import edu.westga.cs3230.furniturerentalsystem.model.ReturnItem;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;

public class ReturnDao {

	public static String addReturn(String memberId, String employeeId) {
		String returnedRentalId = null;
		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING)) {
			String callProcedure = "{CALL AddReturn(?, ?, ?)}";
			try (CallableStatement callableStatement = connection.prepareCall(callProcedure)) {
				returnedRentalId = iterateId();

				callableStatement.setString(1, returnedRentalId);
				callableStatement.setString(2, memberId);
				callableStatement.setString(3, employeeId);

				if (callableStatement.execute()) {
					try (ResultSet resultSet = callableStatement.getResultSet()) {
						if (resultSet.next()) {
							returnedRentalId = resultSet.getString("rental_id");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return returnedRentalId;
	}

	private static String iterateId() throws SQLException {
		String returnId = "0000000000";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING)) {

			String sql = "SELECT return_id FROM `return` ORDER BY return_id DESC LIMIT 1";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String lastReturnId = resultSet.getString("return_id");
						int lastReturnedIdAsInt = Integer.parseInt(lastReturnId) + 1;
						returnId = String.format("%010d", lastReturnedIdAsInt);
						return returnId;

					}
				} catch (SQLException e) {
					e.printStackTrace();
					return returnId;
				}
			}
		}
		return returnId;
	}

	public static void insertReturnItemsIntoDatabase(ArrayList<ReturnItem> returnItemList) {
		String sql = "INSERT INTO return_item (rental_id, furniture_id, return_id, fine_amount, quantity) VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING)) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				for (ReturnItem returnItem : returnItemList) {
					preparedStatement.setString(1, returnItem.getRentalId());
					preparedStatement.setString(2, returnItem.getFurnitureId());
					preparedStatement.setString(3, returnItem.getReturnId());
					preparedStatement.setDouble(4, returnItem.getFineAmount());
					preparedStatement.setInt(5, returnItem.getQuantity());

					preparedStatement.executeUpdate();
				}

				System.out.println("ReturnItems inserted into the database successfully.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateFurnitureQuantityInDatabase(ArrayList<ReturnItem> returnItemList) {

		String sql = "UPDATE furniture SET quantity = quantity + ? WHERE furniture_id = ?";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING)) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				for (ReturnItem returnItem : returnItemList) {
					preparedStatement.setInt(1, returnItem.getQuantity());
					preparedStatement.setString(2, returnItem.getFurnitureId());

					preparedStatement.executeUpdate();
				}

				System.out.println("RentalItems updated in the database successfully.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Return> getAllReturnsForMember(String memberID) {
		ArrayList<Return> returns = new ArrayList<>();
		String selectMember = "SELECT * FROM `return` WHERE member_id = ?;";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(selectMember)) {
			checkStmt.setString(1, memberID);
			try (ResultSet rs = checkStmt.executeQuery()) {
				while (rs.next()) {
					Return rentalInfo = Return.builder().returnId(rs.getString("return_id"))
							.memberId(rs.getString("member_id")).employeeId(rs.getString("employee_num")).build();

					returns.add(rentalInfo);
				}
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
		return returns;
	}

	public static ArrayList<RentalItem> getAllRentalItemsStillCheckedOut(String rentalId) {
		ArrayList<RentalItem> returns = new ArrayList<RentalItem>();

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				// Assuming you have a stored procedure named "your_stored_procedure" that
				// returns a result set
				CallableStatement callableStatement = connection.prepareCall("{call GetUnreturnedItems(?)}")) {
			callableStatement.setString(1, rentalId);

			// Execute the stored procedure and get the result set
			ResultSet resultSet = callableStatement.executeQuery();

			// Process the result set as needed
			while (resultSet.next()) {
				// Retrieve data from the result set
				RentalItem rentalItem = RentalItem.builder().rentalId(resultSet.getString("rental_id")).furnitureId(resultSet.getString("furniture_id")).
						quantity(resultSet.getInt("quantity_difference")).cost(resultSet.getDouble("cost")).build();

				int returnQuantityFromResultSet = resultSet.getInt("return_quantity");
				int quantityDifferenceFromResultSet = resultSet.getInt("quantity_difference");

				// Process or print the retrieved data
//				System.out.println("Rental ID: " + rentalIdFromResultSet);
//				System.out.println("Furniture ID: " + furnitureIdFromResultSet);
//				System.out.println("Rental Quantity: " + rentalQuantityFromResultSet);
				System.out.println("Return Quantity: " + returnQuantityFromResultSet);
				System.out.println("Quantity Difference: " + quantityDifferenceFromResultSet);
				returns.add(rentalItem);
			}
			
			// Close the result set
			resultSet.close();
			return returns;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
