package com.pk.PhotoAppApiUsers.ui.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.pk.PhotoAppApiUsers.ui.dto.UserDto;

public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto user);
	UserDto getUserDetailsByUserName(String email);
}
