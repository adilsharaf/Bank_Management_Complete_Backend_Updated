package com.tcs.serviceimpl;

import com.tcs.dto.RegisterRequestDTO;
import com.tcs.dto.UserResponseDTO;
import com.tcs.entity.User;
import com.tcs.entity.values.Roles;
import com.tcs.entity.values.Status;
import com.tcs.exceptions.UnauthorizedAccessException;
import com.tcs.exceptions.UserNotFoundException;
import com.tcs.repository.UserRepository;
import com.tcs.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createAdmin(RegisterRequestDTO dto) {

        User admin = new User();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword())); 
        admin.setRole(Roles.ADMIN);
        admin.setStatus(Status.ACTIVE);
        admin.setPancardId(dto.getPancardId());

        userRepository.save(admin);
    }

    @Override
    public void blockUser(int userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
        if(user.getRole()==Roles.SUPER_ADMIN) {
        	throw new UnauthorizedAccessException("Restricted action");
        }
        user.setStatus(Status.BLOCKED);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(int userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));

        if(user.getRole()==Roles.SUPER_ADMIN) {
        	throw new UnauthorizedAccessException("Restricted action");
        }
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getUserId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
