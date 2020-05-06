package com.pk.PhotoAppApiUsers.ui.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pk.PhotoAppApiUsers.ui.Validator.User;
import com.pk.PhotoAppApiUsers.ui.dto.UserDto;
import com.pk.PhotoAppApiUsers.ui.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService service;
	
    @GetMapping
    public User getUsers(){
        return new User("pradyumna","Sahani","prad@test.com","pk@123");

    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
    				produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user) {
    	ModelMapper mapper = new ModelMapper();
		UserDto userDto = mapper.map(user, UserDto.class);
		UserDto savedDto = service.createUser(userDto);
		ResponseEntity<UserDto> response = new ResponseEntity<UserDto>(savedDto, HttpStatus.CREATED);
    	return response;
    }
}
