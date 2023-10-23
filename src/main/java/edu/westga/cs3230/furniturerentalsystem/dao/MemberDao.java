package edu.westga.cs3230.furniturerentalsystem.dao;

import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;

@NoArgsConstructor
public class MemberDao {

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> members = new ArrayList<>();
        String selectMember = "SELECT m.member_id, m.pid, m.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `member` m JOIN personal_information pi ON pi.pid = m.pid;";


        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(selectMember)) {

            try (ResultSet rs = checkStmt.executeQuery()) {
                while (rs.next()) {
                    PersonalInformation pInfo = PersonalInformation.builder()
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
                    Member member = Member.builder()
                            .memberId(rs.getString("member_id"))
                            .pId(rs.getString("pid"))
                            .pInfo(pInfo)
                            .build();
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }

    public ArrayList<Member> getMembersByMemberId(String memberId) {
        ArrayList<Member> members = new ArrayList<>();
        String selectMember = "SELECT m.member_id, m.pid, m.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `member` m JOIN personal_information pi ON pi.pid = m.pid where m.member_id = ?;";

        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(selectMember)) {
            checkStmt.setString(1, memberId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                while (rs.next()) {
                    PersonalInformation pInfo = PersonalInformation.builder()
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
                    Member member = Member.builder()
                            .memberId(rs.getString("member_id"))
                            .pId(rs.getString("pid"))
                            .pInfo(pInfo)
                            .build();
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return members;
    }
    public ArrayList<Member> getMembersByPhoneNumber(String phoneNumber) {
        ArrayList<Member> members = new ArrayList<>();
        String selectMember = "SELECT m.member_id, m.pid, m.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `member` m JOIN personal_information pi ON pi.pid = m.pid where pi.phone_num = ?;";

        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(selectMember)) {
            checkStmt.setString(1, phoneNumber);
            try (ResultSet rs = checkStmt.executeQuery()) {
                while (rs.next()) {
                    PersonalInformation pInfo = PersonalInformation.builder()
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
                    Member member = Member.builder()
                            .memberId(rs.getString("member_id"))
                            .pId(rs.getString("pid"))
                            .pInfo(pInfo)
                            .build();
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return members;
    }

    public ArrayList<Member> getMembersByName(String fullName) {
        ArrayList<Member> members = new ArrayList<>();
        String selectMember = "SELECT m.member_id, m.pid, m.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `member`m JOIN personal_information pi ON pi.pid = m.pid WHERE CONCAT (pi.f_name, ' ', pi.l_name) = ?;";

        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(selectMember)) {
            checkStmt.setString(1, fullName);
            try (ResultSet rs = checkStmt.executeQuery()) {
                while (rs.next()) {
                    PersonalInformation pInfo = PersonalInformation.builder()
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
                    Member member = Member.builder()
                            .memberId(rs.getString("member_id"))
                            .pId(rs.getString("pid"))
                            .pInfo(pInfo)
                            .build();
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return members;
    }

    //Todo: Create select statement for member search filters (Phone number, name, member_id)
    // Move some alter user dao functionality to this class
}
