package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDao {

	private static final String CONNECTION_STRING = "jdbc:mysql://160.10.217.6:3306/cs3230f23c?user=cs3230f23c&password=qjvw6rTXAXCmmR7EUBU@";

	public boolean authorizeUser(String username, String password) {
		String query = "select * from user where username = ? and password = ? and role != ?";
		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, "member");

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
		return this.setupUser(username, password, role, pinfo);
	}

	private boolean setupUser(String username, String password, String role, PersonalInformation pinfo) throws IllegalArgumentException {
		if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
			return false;
		}

		try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {
			connection.setAutoCommit(false);
			boolean insertUser = false;
			boolean insertMember = false;
			if (this.checkExists(connection, username)) {
				long pinfoID = this.insertUserInfo(connection, pinfo);
				insertUser = this.insertUserCreds(connection, username, password, role);
				insertMember = this.addMember(connection, pinfoID, username);
			} else {
				throw new IllegalArgumentException(Constants.USERNAME_INUSE);
			}
			connection.setAutoCommit(true);
			connection.close();
			return insertUser && insertMember;
		} catch (SQLException e) {
			throw new IllegalArgumentException(Constants.FAILED_SQL);
		}
	}
	
	private boolean checkExists(Connection conn, String username) throws IllegalArgumentException {
		String checkQuery = "SELECT * FROM user WHERE username = ?;";

		try {
			PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
			checkStmt.setString(1, username);
			ResultSet rs = checkStmt.executeQuery();

			return !rs.next();
		} catch (SQLException e) {
			throw new IllegalArgumentException(Constants.FAILED_SQL);
		}

	}
	
	private boolean insertUserCreds(Connection conn, String username, String password, String role)
			throws IllegalArgumentException {
		String insertUserQuery = "INSERT INTO user (username, password, role) VALUES (?, ?, ?);";
		try (PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {

			insertStmt.setString(1, username);
			insertStmt.setString(2, password);
			insertStmt.setString(3, role);

			int affectedRows = insertStmt.executeUpdate();
			return !(affectedRows == 0);
		} catch (SQLException e) {
			throw new IllegalArgumentException(Constants.FAILED_SQL);
		}
	}

	private long insertUserInfo(Connection conn, PersonalInformation pinfo) throws SQLException {
		String insertInfoQuery = "INSERT INTO personal_information (f_name, l_name, b_date, gender, phone_num, street_add, city, state, zip, register_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement insertStmt = conn.prepareStatement(insertInfoQuery, Statement.RETURN_GENERATED_KEYS)) {

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
			if (affectedRows > 0) {
				try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						long id = generatedKeys.getLong(1);
						return id;
					} else {
						throw new SQLException("Creating user failed, no ID obtained.");
					}
				}
			}

			return 0;
		}
	}
	
	
	private boolean addMember(Connection conn, long pid, String username) throws SQLException {
		String uid = this.generateTenDigitID();
		String insertMember = "INSERT INTO member (member_id,pid,username) VALUES (?,?,?);";
		try (PreparedStatement checkStmt = conn.prepareStatement(insertMember)) {
			checkStmt.setString(1, uid);
			checkStmt.setInt(2, (int) pid);
			checkStmt.setString(3, username);

			int result = checkStmt.executeUpdate();
			
			return result > 0;
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
					pInfo = PersonalInformation.builder().firstName(rs.getString("f_name"))
							.lastName(rs.getString("l_name")).registrationDate(rs.getDate("register_date"))
							.gender(rs.getString("gender")).phoneNumber(rs.getString("phone_num"))
							.birthday(rs.getDate("b_date")).address(rs.getString("street_add"))
							.city(rs.getString("city")).state(rs.getString("state")).zip(rs.getString("zip")).build();
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return pInfo;
	}



	private String generateTenDigitID() {
		Random random = new Random();
		int firstDigit = 1 + random.nextInt(9);
		int remainingDigits = random.nextInt(1_000_000_000);

		return String.valueOf(firstDigit) + String.format("%09d", remainingDigits);
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
	
	public boolean alterUser(String memberId, PersonalInformation pinfo) {
	    String callProcedure = "{CALL UpdateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	    try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
	         CallableStatement callStmt = connection.prepareCall(callProcedure)) {

	        java.sql.Date sqlBirthDate = java.sql.Date.valueOf(pinfo.getBirthday().toString());

	        callStmt.setString(1, memberId);
	        callStmt.setString(2, pinfo.getFirstName());
	        callStmt.setString(3, pinfo.getLastName());
	        callStmt.setDate(4, sqlBirthDate);
	        callStmt.setString(5, pinfo.getGender());
	        callStmt.setString(6, pinfo.getPhoneNumber());
	        callStmt.setString(7, pinfo.getAddress());
	        callStmt.setString(8, pinfo.getCity());
	        callStmt.setString(9, pinfo.getState());
	        callStmt.setString(10, pinfo.getZip());

	        int affectedRows = callStmt.executeUpdate();
	        return affectedRows > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
