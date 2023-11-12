package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.westga.cs3230.furniturerentalsystem.model.Rental;
import edu.westga.cs3230.furniturerentalsystem.model.RentalItem;
import edu.westga.cs3230.furniturerentalsystem.model.Transaction;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;

public class RentalDao {

	public static String addRental(Transaction rental, Date startDate, Date endDate) {
		String returnedRentalId = null;
		String furnitureIds = rental.getFurnitureIds();
		String quantities = rental.getQuantities();
		String costs = rental.getCosts();

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING)) {
			String callProcedure = "{CALL AddRentalWithMultipleItems(?, ?, ?, ?, ?, ?, ?)}";
			try (CallableStatement callableStatement = connection.prepareCall(callProcedure)) {
				callableStatement.setString(1, rental.getMemberId());
				callableStatement.setString(2, rental.getEmployeeNum());

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				callableStatement.setString(3, format.format(startDate));
				callableStatement.setString(4, format.format(endDate));

				callableStatement.setString(5, furnitureIds);
				callableStatement.setString(6, quantities);
				callableStatement.setString(7, costs);

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

	public ArrayList<Rental> getAllRentalsForMember(String memberID) {
		ArrayList<Rental> rentals = new ArrayList<>();
		String selectMember = "SELECT * FROM `rental` WHERE member_id = ?;";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(selectMember)) {
			checkStmt.setString(1, memberID);
			try (ResultSet rs = checkStmt.executeQuery()) {
				while (rs.next()) {
					Rental rentalInfo = Rental.builder().rentalId(rs.getString("rental_id"))
							.memberId(rs.getString("member_id")).employeeId(rs.getString("employee_num"))
							.startDate(rs.getDate("start_date")).dueDate(rs.getDate("due_date")).build();

					rentals.add(rentalInfo);
				}
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}

		return rentals;
	}
	
	public ArrayList<Rental> getAllRentals() {
		ArrayList<Rental> rentals = new ArrayList<>();
		String allRentalsQuery = "SELECT * FROM `rental`;";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(allRentalsQuery)) {
			try (ResultSet rs = checkStmt.executeQuery()) {
				while (rs.next()) {
					Rental rentalInfo = Rental.builder().rentalId(rs.getString("rental_id"))
							.memberId(rs.getString("member_id")).employeeId(rs.getString("employee_num"))
							.startDate(rs.getDate("start_date")).dueDate(rs.getDate("due_date")).build();

					rentals.add(rentalInfo);
				}
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}

		return rentals;
	}
	
	public ArrayList<RentalItem> getRentalItemsFromRental(String rentalId) {
		ArrayList<RentalItem> rentalItems = new ArrayList<>();
		String selectMember = "SELECT * FROM `rental_item` where rental_id = ?;";

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
				PreparedStatement checkStmt = connection.prepareStatement(selectMember)){
			checkStmt.setString(1, rentalId);
		
			try (ResultSet rs = checkStmt.executeQuery()) {
				while (rs.next()) {
					RentalItem rentalItem = RentalItem.builder().rentalId(rs.getString("rental_id")).furnitureId(rs.getString("furniture_id"))
							.quantity(rs.getInt("quantity")).cost(rs.getDouble("cost")).build();
							

					rentalItems.add(rentalItem);
				}
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}

		return rentalItems;
	}
	
	

}
