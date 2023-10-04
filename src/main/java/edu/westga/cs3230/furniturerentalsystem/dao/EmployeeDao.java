package edu.westga.cs3230.furniturerentalsystem.dao;

import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeDao {

    public boolean authorizeEmployee(String username, String password){
        //Todo: write sql statement to check if this username and password exists.
        return false;
    }

    public boolean registerEmployee(Employee employee){
        //Todo: write insert statement to register employee
        return false;
    }
}
