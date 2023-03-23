package com.project.rest.webservices.restfuwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.rest.webservices.restfuwebservices.jpadao.LoginUserRepository;

@Component
public class LoginUserService {
	
	@Autowired
	private LoginUserRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public String addUser(LoginUsersInfo loginUser) {
		loginUser.setPassword(encoder.encode(loginUser.getPassword()));
		repo.save(loginUser);
		return "user added";
	}

}
