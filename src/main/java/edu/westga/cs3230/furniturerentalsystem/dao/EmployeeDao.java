package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.*;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
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

    public static ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        String selectEmployee = "SELECT e.employee_num, e.pid, e.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `employee` e JOIN personal_information pi ON pi.pid = e.pid;";

        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(selectEmployee)) {

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
                    Employee employee = Employee.builder()
                            .employeeNum(rs.getString("employee_num"))
                            .pId(rs.getString("pid"))
                            .pInfo(pInfo)
                            .build();
                    employees.add(employee);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return employees;
    }

    public static ArrayList<Employee> getEmployeesByEmployeeNumber(String employeeNumber) {
        ArrayList<Employee> employees = new ArrayList<>();
        String selectEmployee = "SELECT e.employee_num, e.pid, e.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `employee` e JOIN personal_information pi ON pi.pid = e.pid WHERE e.employee_num = ?;";

        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(selectEmployee)) {
            checkStmt.setString(1, employeeNumber);
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
                    Employee employee = Employee.builder()
                            .employeeNum(rs.getString("employee_num"))
                            .pId(rs.getString("pid"))
                            .pInfo(pInfo)
                            .build();
                    employees.add(employee);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return employees;
    }

}
