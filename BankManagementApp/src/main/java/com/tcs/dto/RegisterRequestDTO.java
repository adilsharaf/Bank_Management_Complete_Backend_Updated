package com.tcs.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


public class RegisterRequestDTO {

    private String name;
    private String email;
    private String password;
    
    private String pancardId;
	@Override
	public String toString() {
		return "RegisterRequestDTO [name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	public RegisterRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegisterRequestDTO(String name, String email, String password, String pancardId) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.pancardId = pancardId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPancardId() {
		return pancardId;
	}
	public void setPancardId(String pancardId) {
		this.pancardId = pancardId;
	}
    
    
}

