package com.tcs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


public class LoginRequestDTO {

    private String email;
    private String password;
	public LoginRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginRequestDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
