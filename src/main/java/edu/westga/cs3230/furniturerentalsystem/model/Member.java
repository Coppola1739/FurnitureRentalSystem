package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Member model class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member {
    @NonNull
    private String memberId;
    @NonNull
    private String pId;
    @NonNull
    private PersonalInformation pInfo;

    /**
     * To String method
     *
     * @return String the output string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Member ID: ").append(this.memberId).append(" ");
        output.append("Name: ").append(this.pInfo.getFirstName()).append(" ").append(this.pInfo.getLastName());
        return output.toString();
    }

    public String getMemberId() {
    	return this.memberId;
    }
    
    public PersonalInformation getPInfo() {
    	return this.pInfo;
    }
    
    public static Builder builder() {
    	return new Builder();
    }
    
    public static class Builder{
    	private PersonalInformation pInfo;
    	private Member member;
    	public Builder() {
    		
    	}
    	
    	public Builder pInfo(PersonalInformation pInfo) {
    		this.pInfo = pInfo;
    		return this;
    	}
    	
    	public Builder memberId(String memberId) {
    		member.memberId = memberId;
    		return this;
    	}
    	
    	public Builder pId(String pId) {
    		member.pId = pId;
    		return this;
    	}
    	
    	public Member build() {
    		return member;
    	}
    }
}
