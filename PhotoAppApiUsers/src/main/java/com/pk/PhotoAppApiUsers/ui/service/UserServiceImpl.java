package com.pk.PhotoAppApiUsers.ui.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.pk.PhotoAppApiUsers.ui.Entity.UserEntity;
import com.pk.PhotoAppApiUsers.ui.Repository.UserRepository;
import com.pk.PhotoAppApiUsers.ui.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Override
	public UserDto createUser(UserDto user) {
		user.setUserId(UUID.randomUUID().toString());
		
		ModelMapper mapper = new ModelMapper();					
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(user, UserEntity.class);
		
		userEntity.setEncriptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(UUID.randomUUID().toString());
		UserEntity savedEntity = userRepository.save(userEntity);
		
		UserDto savedUserDto = mapper.map(savedEntity, UserDto.class);
		savedUserDto.setEncriptedPassword(null);
		return savedUserDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if(userEntity==null) throw new UsernameNotFoundException("User Name Not Found :" + username);
		User user = new User(username, userEntity.getEncriptedPassword(), true, true, true, true, new ArrayList<>());
		return user;
	}

	@Override
	public UserDto getUserDetailsByUserName(String email) {
		UserEntity userEntity =  userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException("User not found in getUserDetailsByUserName");
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	
}