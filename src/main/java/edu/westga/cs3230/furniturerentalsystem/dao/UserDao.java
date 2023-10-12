package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.DbUtil;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDao {
	
	public boolean authorizeUser(String username, String password) {
        //Todo: write sql statement to check if this username and password exists.
        String query = "select * from user where username = ? and password = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://160.10.217.6:3306/cs3230f23c?user=cs3230f23c&password=qjvw6rTXAXCmmR7EUBU@");
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	System.out.println("Successfull login");
            	
                return true;
            }
        }catch(Exception e) {
        	System.out.println("Failed to connect");
        	return false;
        }
        System.out.println("Unsuccessfull login");
        return false;
    }
	
	public boolean registerUser(String username, String password, String role, PersonalInformation pinfo) {
		try {
			return this.setupUser(username, password, role, pinfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
		private boolean setupUser(String username, String password, String role, PersonalInformation pinfo) throws SQLException {
	        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
	            return false;
	        }

	        
	        String checkQuery = "SELECT * FROM user WHERE username = ?";
	        try (Connection connection = DriverManager.getConnection("jdbc:mysql://160.10.217.6:3306/cs3230f23c?user=cs3230f23c&password=qjvw6rTXAXCmmR7EUBU@")){
	             PreparedStatement checkStmt = connection.prepareStatement(checkQuery); 

	            checkStmt.setString(1, username);
	            ResultSet rs = checkStmt.executeQuery();

	            if (rs.next()) {
	                
	                return false;
	            }
	        }
	        
	        

	        String insertUserQuery = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
	        try (Connection connection = DriverManager.getConnection("jdbc:mysql://160.10.217.6:3306/cs3230f23c?user=cs3230f23c&password=qjvw6rTXAXCmmR7EUBU@");
	             PreparedStatement insertStmt = connection.prepareStatement(insertUserQuery)) {

	            insertStmt.setString(1, username);
	            insertStmt.setString(2, password);  
	            insertStmt.setString(3, role);  

	            int affectedRows = insertStmt.executeUpdate();
	            if( affectedRows == 0) {
	            	return false;
	            }
	        }
	        
	        String insertInfoQuery = "INSERT INTO personal_information (f_name, l_name, b_date, gender, phone_num, street_add, city, state, zip, register_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        try (Connection connection = DriverManager.getConnection("jdbc:mysql://160.10.217.6:3306/cs3230f23c?user=cs3230f23c&password=qjvw6rTXAXCmmR7EUBU@");
	             PreparedStatement insertStmt = connection.prepareStatement(insertInfoQuery)) {

	        	java.sql.Date sqlBirthDate = new java.sql.Date( pinfo.getBirthday().getTime() );
	        	java.sql.Date sqlRegDate = new java.sql.Date( pinfo.getRegistrationDate().getTime() );
	            insertStmt.setString(1, pinfo.getFirstName());
	            insertStmt.setString(2, pinfo.getLastName());  
	            insertStmt.setString(3, sqlBirthDate.toString());
	            insertStmt.setString(4, pinfo.getGender());
	            insertStmt.setString(5, pinfo.getPhoneNumber());
	            insertStmt.setString(6, pinfo.getAddress());
	            insertStmt.setString(7, pinfo.getCity());
	            insertStmt.setString(8, pinfo.getState());
	            insertStmt.setString(9, pinfo.getZip());
	            insertStmt.setString(10, sqlRegDate.toString());

	            int affectedRows = insertStmt.executeUpdate();
	            return affectedRows > 0;
	        }
	}
		
		
			
		
}
