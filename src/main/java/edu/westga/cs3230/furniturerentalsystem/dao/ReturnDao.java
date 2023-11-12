package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.furniturerentalsystem.util.Constants;

public class ReturnDao {

	public static String addReturn(String memberId, String employeeId) {
		String returnedRentalId = null;
		System.out.println(memberId);
//		String furnitureIds = rental.getFurnitureIds();
//		String quantities = rental.getQuantities();
//		String costs = rental.getCosts();

		try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING)) {
			String callProcedure = "{CALL AddReturn(?, ?, ?)}";
			try (CallableStatement callableStatement = connection.prepareCall(callProcedure)) {
				returnedRentalId = iterateId();

				callableStatement.setString(1, returnedRentalId);
				callableStatement.setString(2, memberId);
				callableStatement.setString(3, employeeId);

				//
				System.out.println("Stored procedure executed successfully!");
				callableStatement.execute();
//				if (callableStatement.execute()) {
//					try (ResultSet resultSet = callableStatement.getResultSet()) {
//						if (resultSet.next()) {
//							returnedRentalId = resultSet.getString("rental_id");
//						}
//					}
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

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						String lastReturnId = resultSet.getString("return_id");
                        System.out.println("Last Return ID: " + lastReturnId);
						int lastReturnIDasInt = Integer.parseInt(lastReturnId) + 1;
//						int returnIdAsInt = Integer.parseInt(returnId);
						returnId = String.format("%010d", lastReturnIDasInt);
						System.out.println(lastReturnId);
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
}
