package edu.westga.cs3230.furniturerentalsystem.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PersonalInformation {

	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private Date registrationDate;
	@NonNull
	private String gender;
	@NonNull
	private String phoneNumber;
	@NonNull
	private Date birthday;
	@NonNull
	private String address;
	@NonNull
	private String city;
	@NonNull
	private String state;
	@NonNull
	private String zip;
	
	public PersonalInformation(@NonNull String firstName, @NonNull String lastName, @NonNull Date registrationDate,
			@NonNull String gender, @NonNull String phoneNumber, @NonNull Date birthday, @NonNull String address,
			@NonNull String city, @NonNull String state, @NonNull String zip) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.registrationDate = registrationDate;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.birthday = birthday;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
}
