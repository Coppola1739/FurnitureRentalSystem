package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.westga.cs3230.furniturerentalsystem.util.Constants;

public class EmployeeDao {

	public static String getEmployeeNumByUsername(String username) throws Exception {
        String employeeNum = null;

        
        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING)) {
            
            String sql = "SELECT e.employee_num FROM Employee e WHERE e.username = ?";

            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        employeeNum = resultSet.getString("employee_num");
                    }
                }
            }
        }

        return employeeNum;
    }
	
}
