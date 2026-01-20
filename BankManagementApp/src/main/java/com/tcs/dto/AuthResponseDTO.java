package com.tcs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.tcs.entity.values.Roles;

import lombok.AllArgsConstructor;


public class AuthResponseDTO {

    private String token;
    private Roles role;
    private int userId;
	public AuthResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthResponseDTO(String token, Roles role, int userId) {
		super();
		this.token = token;
		this.role = role;
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
    
    
}
