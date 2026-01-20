package com.tcs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.dto.UserResponseDTO;
import com.tcs.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	Optional<User> findByPancardId(String email);
	
	
}
