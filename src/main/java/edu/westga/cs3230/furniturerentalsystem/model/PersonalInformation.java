package edu.westga.cs3230.furniturerentalsystem.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Personal Information model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
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
    
//    public String getFirstName() {
//		return this.firstName;
//	}
//	
//	public String getLastName() {
//		return this.lastName;
//	}
//	
//	public Date getRegistrationDate() {
//		return this.registrationDate;
//	}
//	
//	public String getGender() {
//		return this.gender;
//	}
//	
//	public Date getBirthday() {
//		return this.birthday;
//	}
//	
//	public String getAddress() {
//		return this.address;
//	}
//	
//	public String getCity() {
//		return this.city;
//	}
//	
//	public String getState() {
//		return this.state;
//	}
//	
//	public String getZip() {
//		return this.zip;
//	}
//	
//	public String getPhoneNumber() {
//		return this.phoneNumber;
//	}
//
//	public static Builder builder() {
//        return new Builder();
//    }
//	
//	public static class Builder {
//        private PersonalInformation pInfo;
//
//        public Builder() {
//            pInfo = new PersonalInformation();
//        }
//
//        public Builder firstName(String firstName) {
//            pInfo.firstName = firstName;
//            return this;
//        }
//
//        public Builder lastName(String lastName) {
//            pInfo.lastName = lastName;
//            return this;
//        }
//
//        public Builder registrationDate(Date registrationDate) {
//            pInfo.registrationDate = registrationDate;
//            return this;
//        }
//
//        public Builder gender(String gender) {
//            pInfo.gender = gender;
//            return this;
//        }
//
//        public Builder phoneNumber(String phoneNumber) {
//            pInfo.phoneNumber = phoneNumber;
//            return this;
//        }
//
//        public Builder birthday(Date birthday) {
//            pInfo.birthday = birthday;
//            return this;
//        }
//
//        public Builder address(String address) {
//            pInfo.address = address;
//            return this;
//        }
//
//        public Builder city(String city) {
//            pInfo.city = city;
//            return this;
//        }
//
//        public Builder state(String state) {
//            pInfo.state = state;
//            return this;
//        }
//
//        public Builder zip(String zip) {
//            pInfo.zip = zip;
//            return this;
//        }
//
//        public PersonalInformation build() {
//            return pInfo;
//        }
//    }
}
