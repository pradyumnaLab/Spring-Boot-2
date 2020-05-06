package com.pk.PhotoAppApiUsers.Security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pk.PhotoAppApiUsers.ui.service.UserService;

@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//http.authorizeRequests().antMatchers("/**").permitAll();
		http.addFilter(getAuthenticationManager());
		http.headers().frameOptions().disable();
	}

	private Filter getAuthenticationManager() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	
}
