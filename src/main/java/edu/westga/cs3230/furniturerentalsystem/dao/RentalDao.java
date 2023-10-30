package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}
