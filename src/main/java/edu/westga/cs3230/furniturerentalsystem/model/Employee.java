package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Employee {

    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Date hireDate;
    @NonNull
    private String gender;
    @NonNull
    private String phoneNumber;
    @NonNull
    private Date birthday;
    
    public Employee(@NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull String lastName, @NonNull Date hireDate, @NonNull String gender, @NonNull String phoneNumber, @NonNull Date birthday) throws IllegalArgumentException {
    	if ( username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if ( password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if ( firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (gender.isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
        if (phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
     
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
    
    
}
