package com.tcs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.tcs.entity.values.Roles;
import com.tcs.entity.values.Status;

import lombok.AllArgsConstructor;


public class UserResponseDTO {

    private int userId;
    private String name;
    private String email;
    private Roles role;
    private Status status;
    private String password;
    
    
	public UserResponseDTO() {
		super();
		
	}

	public UserResponseDTO(int userId, String name, String email, Roles role, Status status) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.role = role;
		this.status = status;
	}
	
	public UserResponseDTO(int userId, String name, String email, Roles role, Status status, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.password = password;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
    
    
}
