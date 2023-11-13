package edu.westga.cs3230.furniturerentalsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Return {

	String returnId;
	String memberId;
	String employeeId;
	
	
	@Override
    public String toString() {
        return "Return{" +
                "returnId='" + returnId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
