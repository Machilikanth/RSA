package com.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
	 private String id; 
	    private String stuid;
	    private String name;
	    private int age;
	    private String address;
	    private String email;
	    private String privateKey;
}
