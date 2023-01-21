package com.project.rest.webservices.restfuwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<Users> users = new ArrayList<>();
	
	private static int userCount=0;
	
	static {
		users.add(new Users(userCount++,"Adam",LocalDate.now().minusYears(15)));
		users.add(new Users(userCount++,"Eve",LocalDate.now().minusYears(10)));
		users.add(new Users(userCount++,"Jim",LocalDate.now().minusYears(3)));
	}
	
	public List<Users> findAll() {
		return users;
	}
	
	public Users findOne(int id) {
		Predicate<? super Users> predicate = user -> user.getId().equals(id); 
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public Users saveUser(Users user) {
		user.setId(userCount++);
		users.add(user);
		return user;
		
	}

	public Users deleteUserById(int id) {
		for(Users user:users) {
			if(id==user.getId()) {
				 users.remove(user);
				 return user;
			}
		}
		return null;
	}

}
