package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDao {

	private static final String CONNECTION_STRING = "jdbc:mysql://160.10.217.6:3306/cs3230f23c?user=cs3230f23c&password=qjvw6rTXAXCmmR7EUBU@";

	public boolean authorizeUser(String username, String password) {
		// Todo: write sql statement to check if this username and password exists.
		String query = "select * from user where username = ? and password = ?";
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, username);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				System.out.println("Successfull login");

				return true;
			}
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("Unsuccessfull login");
		return false;
	}

	public boolean registerUser(String username, String password, String role, PersonalInformation pinfo) {
		try {
			return this.setupUser(username, password, role, pinfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean setupUser(String username, String password, String role, PersonalInformation pinfo)
			throws SQLException {
		if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
			return false;
		}

		if (this.checkExists(username)) {

			return (this.insertUserInfo(pinfo) && this.insertUserCreds(username, password, role));
		} else {
			return false;
		}

	}

	private boolean checkExists(String username) throws SQLException {
		String checkQuery = "SELECT * FROM user WHERE username = ?";
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			PreparedStatement checkStmt = connection.prepareStatement(checkQuery);

			checkStmt.setString(1, username);
			ResultSet rs = checkStmt.executeQuery();

			return !rs.next();
		} catch (Exception e) {
			return false;
		}
	}

	private boolean insertUserCreds(String username, String password, String role) throws SQLException {
		String insertUserQuery = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement insertStmt = connection.prepareStatement(insertUserQuery)) {

			insertStmt.setString(1, username);
			insertStmt.setString(2, password);
			insertStmt.setString(3, role);

			int affectedRows = insertStmt.executeUpdate();
			return !(affectedRows == 0);
		} catch (Exception e) {
			return false;
		}
	}

	private boolean insertUserInfo(PersonalInformation pinfo) throws SQLException {
		String insertInfoQuery = "INSERT INTO personal_information (f_name, l_name, b_date, gender, phone_num, street_add, city, state, zip, register_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement insertStmt = connection.prepareStatement(insertInfoQuery)) {

			java.sql.Date sqlBirthDate = new java.sql.Date(pinfo.getBirthday().getTime());
			java.sql.Date sqlRegDate = new java.sql.Date(pinfo.getRegistrationDate().getTime());
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

	public PersonalInformation selectUserInformation(String username) {
	    String selectUser = "SELECT pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `member` m JOIN personal_information pi ON pi.pid = m.pid where m.username = ?;";
	    PersonalInformation pInfo = null;
	    
	    try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
	         PreparedStatement checkStmt = connection.prepareStatement(selectUser)) {

	        checkStmt.setString(1, username);
	        try (ResultSet rs = checkStmt.executeQuery()) {
	            if (rs.next()) {
	                pInfo = PersonalInformation.builder()
	                        .firstName(rs.getString("f_name"))
	                        .lastName(rs.getString("l_name"))
	                        .registrationDate(rs.getDate("register_date"))
	                        .gender(rs.getString("gender"))
	                        .phoneNumber(rs.getString("phone_num"))
	                        .birthday(rs.getDate("b_date"))
	                        .address(rs.getString("street_add"))
	                        .city(rs.getString("city"))
	                        .state(rs.getString("state"))
	                        .zip(rs.getString("zip"))
	                        .build();
	            }
	        }
	        
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    
	    return pInfo;
	}


	/**
	 * Alters a user by updating personal information fields concurrently.
	 * 
	 * @param username the registered username
	 * @param password the password associated with that username
	 * @param role     employee, member, or manager
	 * @param pinfo    A personalInformation item with NO EMPTY STRINGS
	 * @return true if rows affected, false otherwise
	 * @throws SQLException shouldn't be thrown unless bad information is entered in
	 *                      pInfo
	 */
	public boolean alterUser(String username, String password, String role, PersonalInformation pinfo)
			throws SQLException {
		String alterInfoQuery = "UPDATE personal_information JOIN member ON personal_information.pid = member.pid AND member.username = ? SET f_name = ?, l_name = ?, b_date = ?, gender = ?, phone_num = ?, street_add = ?, city = ?, state = ?, zip = ?";
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement insertStmt = connection.prepareStatement(alterInfoQuery)) {

			java.sql.Date sqlBirthDate = new java.sql.Date(pinfo.getBirthday().getTime());
			insertStmt.setString(1, username);
			insertStmt.setString(2, pinfo.getFirstName());
			insertStmt.setString(3, pinfo.getLastName());
			insertStmt.setString(4, sqlBirthDate.toString());
			insertStmt.setString(5, pinfo.getGender());
			insertStmt.setString(6, pinfo.getPhoneNumber());
			insertStmt.setString(7, pinfo.getAddress());
			insertStmt.setString(8, pinfo.getCity());
			insertStmt.setString(9, pinfo.getState());
			insertStmt.setString(10, pinfo.getZip());

			int affectedRows = insertStmt.executeUpdate();
			return affectedRows > 0;
		}
	}

}
