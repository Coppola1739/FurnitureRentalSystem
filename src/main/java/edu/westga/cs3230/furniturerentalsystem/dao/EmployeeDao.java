package edu.westga.cs3230.furniturerentalsystem.dao;

import java.sql.*;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import edu.westga.cs3230.furniturerentalsystem.util.UserStatus;

import static edu.westga.cs3230.furniturerentalsystem.util.Constants.CONNECTION_STRING;

public class EmployeeDao {

    public static String getEmployeeNumByUsername(String username) throws Exception {
        String employeeNum = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING)) {

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
    public static ArrayList<Employee> getAllEnabledEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        String selectEmployee = "SELECT e.employee_num, e.pid, e.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip, u.role FROM `employee` e JOIN personal_information pi ON pi.pid = e.pid JOIN user u on u.username = e.username WHERE u.role != 'disabled';";

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
                            .username(rs.getString("username"))
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
    public static ArrayList<Employee> getEmployeeByUsername(String username){
        ArrayList<Employee> employees = new ArrayList<>();
        String selectEmployee = "SELECT e.employee_num, e.pid, e.username, pi.f_name, pi.l_name, pi.register_date, pi.gender, pi.phone_num, pi.b_date, pi.street_add, pi.city, pi.state, pi.zip FROM `employee` e JOIN personal_information pi ON pi.pid = e.pid WHERE e.username = ?;";

        try (Connection connection = DriverManager.getConnection(Constants.CONNECTION_STRING);
             PreparedStatement checkStmt = connection.prepareStatement(selectEmployee)) {
            checkStmt.setString(1, username);
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
                            .username(rs.getString("username"))
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
                            .username(rs.getString("username"))
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

    public boolean alterEmployee(String employeeNumber, PersonalInformation pinfo) {
        String callProcedure = "{CALL UpdateEmployee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             CallableStatement callStmt = connection.prepareCall(callProcedure)) {

            java.sql.Date sqlBirthDate = java.sql.Date.valueOf(pinfo.getBirthday().toString());

            callStmt.setString(1, employeeNumber);
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

    public boolean disableEmployee(Employee employee) throws SQLException {
        String callProcedure = "{CALL ChangeEmployeeRole(?, ?)}";
        String employeeUsername = employee.getUsername();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             CallableStatement callStmt = connection.prepareCall(callProcedure)) {
            callStmt.setString(1, employeeUsername);
            callStmt.setString(2, UserStatus.DISABLED.toString().toLowerCase());

            int affectedRows = callStmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}