package com.pk.PhotoAppApiUsers.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.PhotoAppApiUsers.ui.Validator.LoginValidator;
import com.pk.PhotoAppApiUsers.ui.dto.UserDto;
import com.pk.PhotoAppApiUsers.ui.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService service;
	private Environment env;

	@Autowired
	public AuthenticationFilter(UserService service, Environment env, AuthenticationManager authenticationManager) {
		this.service = service;
		this.env = env;
		super.setAuthenticationManager(authenticationManager);
	}

	public AuthenticationFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {

			LoginValidator creds = new ObjectMapper().readValue(request.getInputStream(), LoginValidator.class);
			return this.getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException("Exception in Authentication...");
		}
	}

	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((User) authResult.getPrincipal()).getUsername();
		UserDto userDto = service.getUserDetailsByUserName(username);

		String token = Jwts.builder().setSubject(userDto.getUserId())
				.setExpiration(
						new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration.time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("secret.key")).compact();
		response.addHeader("jw Token", token);
		response.addHeader("userId", userDto.getUserId());
	}
}
