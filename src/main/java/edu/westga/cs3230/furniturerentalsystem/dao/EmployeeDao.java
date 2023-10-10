package edu.westga.cs3230.furniturerentalsystem.dao;

import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.util.DbUtil;
import lombok.NoArgsConstructor;

import java.sql.*;

@NoArgsConstructor
public class EmployeeDao {

    public boolean authorizeEmployee(String username, String password) throws SQLException {
        //Todo: write sql statement to check if this username and password exists.
        String query = "select * from login where username = ? and password = ?";
        try (Connection connection = DbUtil.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public boolean registerEmployee(Employee employee) {
        //Todo: write insert statement to register employee
        return false;
    }
    
}
