package com.project.rest.webservices.restfuwebservices.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project.rest.webservices.restfuwebservices.config.LoginUserInfoUserDetails;
import com.project.rest.webservices.restfuwebservices.jpadao.LoginUserRepository;

@Component
public class LoginUserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private LoginUserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<LoginUsersInfo> userInfo = repo.findByName(username);
		
		return userInfo.map(LoginUserInfoUserDetails::new)
		.orElseThrow(()->new UserNotFoundException("User Not Found"));
	}

}
