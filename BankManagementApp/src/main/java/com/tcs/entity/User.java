package com.tcs.entity;

import com.tcs.entity.values.Roles;
import com.tcs.entity.values.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	private String name;
	private String email;
	private String password;
	private Roles role;
	private String pancardId;
	private Status status;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String name, String email, String password, Roles role, Status status,long pancarId) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.status = status;
		this.pancardId=pancardId;
		
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public String getPancardId() {
		return pancardId;
	}
	public void setPancardId(String pancardId) {
		this.pancardId = pancardId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
}
