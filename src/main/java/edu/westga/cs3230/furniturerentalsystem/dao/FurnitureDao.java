package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;

/**
 * Furniture DAO
 * Class for accessing furniture table in database
 */

public class FurnitureDao {
//	public ArrayList<Member> getAllMembers() {
//        ArrayList<Member> members = new ArrayList<>();
//        String selectFurniture = "SELECT * FROM `furniture`";
//
//        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
//             PreparedStatement checkStmt = connection.prepareStatement(selectMember)) {
//
//            try (ResultSet rs = checkStmt.executeQuery()) {
//                while (rs.next()) {
//                    PersonalInformation pInfo = PersonalInformation.builder()
//                            .firstName(rs.getString("f_name"))
//                            .lastName(rs.getString("l_name"))
//                            .registrationDate(rs.getDate("register_date"))
//                            .gender(rs.getString("gender"))
//                            .phoneNumber(rs.getString("phone_num"))
//                            .birthday(rs.getDate("b_date"))
//                            .address(rs.getString("street_add"))
//                            .city(rs.getString("city"))
//                            .state(rs.getString("state"))
//                            .zip(rs.getString("zip"))
//                            .build();
//                    Member member = Member.builder()
//                            .memberId(rs.getString("member_id"))
//                            .pId(rs.getString("pid"))
//                            .pInfo(pInfo)
//                            .build();
//                    members.add(member);
//                }
//            }
//        } catch (SQLException exception) {
//            throw new RuntimeException(exception);
//        }
//        return members;
	

}
