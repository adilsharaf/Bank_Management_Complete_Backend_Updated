package com.tcs.serviceimpl;

import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.dto.AuthResponseDTO;
import com.tcs.dto.LoginRequestDTO;
import com.tcs.dto.RegisterRequestDTO;
import com.tcs.dto.UserResponseDTO;
import com.tcs.entity.User;
import com.tcs.entity.values.Roles;
import com.tcs.entity.values.Status;
import com.tcs.exceptions.InvalidCredentialsException;
import com.tcs.exceptions.InvalidInputException;
import com.tcs.exceptions.UnauthorizedAccessException;
import com.tcs.exceptions.UserNotFoundException;
import com.tcs.repository.UserRepository;
import com.tcs.security.JwtUtil;
import com.tcs.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtUtil jwtUtil;

   
	@Override
	public UserResponseDTO  registerUser(RegisterRequestDTO registerRequestDTO) {
		
		if (registerRequestDTO.getName() == null || registerRequestDTO.getName().isBlank() ||
		        registerRequestDTO.getEmail()== null || registerRequestDTO.getEmail().isBlank() ||
		        registerRequestDTO.getPassword() == null || registerRequestDTO.getPassword().isBlank() ||
		        registerRequestDTO.getPancardId()==null || registerRequestDTO.getPancardId().isBlank()) {
				
			System.out.println(registerRequestDTO);
			throw new InvalidInputException("Required fields are missing");
		}
		
		String panRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
	    if (!registerRequestDTO.getPancardId().matches(panRegex)) {
	        throw new InvalidInputException("Invalid PAN card format. Example: ABCDE1234F");
	    }
	    if (userRepository.findByPancardId(registerRequestDTO.getPancardId()).isPresent()) {
	        throw new InvalidInputException("PAN card already registered");
	    }
	    
	    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	    if (!registerRequestDTO.getEmail().matches(emailRegex)) {
	        throw new InvalidInputException("Invalid email format. Please provide a valid email.");
	    }
		
		 if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
		        throw new InvalidInputException("User already exists with this email");
		    }
		 
		 
			User user=new User();
			user.setName(registerRequestDTO.getName());
		    user.setEmail(registerRequestDTO.getEmail());
		    user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		    user.setRole(Roles.CUSTOMER);
		    user.setStatus(Status.ACTIVE);
		    user.setPancardId(registerRequestDTO.getPancardId());
		    
		    User savedUser=userRepository.save(user);
		    
		    return new UserResponseDTO( 
		    		savedUser.getUserId(),
		            savedUser.getName(),
		            savedUser.getEmail(),
		            savedUser.getRole(),
		            savedUser.getStatus()
		    		);
	}

	@Override
	public AuthResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
		
		if (loginRequestDTO.getEmail() == null || loginRequestDTO.getEmail().isBlank() ||
			    loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isBlank()) {
			    throw new InvalidInputException("Email and password are required");
			} 

		User user = userRepository.findByEmail(loginRequestDTO.getEmail())
		        .orElseThrow(() -> new UserNotFoundException("User not found"));

		 
		if(user.getStatus()==Status.BLOCKED) {
			throw new UnauthorizedAccessException("User is blocked. Contact admin.");
		}
		if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid password");
		}

		String token=generateJWTToken(user);
		
		return new AuthResponseDTO(
			    token,
			    user.getRole(),
			    user.getUserId()
			);

	}	

	@Override
	public String generateJWTToken(User user) {
		return jwtUtil.generateToken(user);
	}

}
