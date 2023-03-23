package com.project.rest.webservices.restfuwebservices.first;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.rest.webservices.restfuwebservices.user.LoginUserService;
import com.project.rest.webservices.restfuwebservices.user.LoginUsersInfo;

@RestController
public class FirstController {
	
	// /basic api
	
//	@RequestMapping(method=RequestMethod.GET, path="/firstApi")
//	public String helloWorld() {
//		return "Basic Rest API";
//	}
	
	@Autowired
	private MessageSource msource;
	
	@Autowired
	private LoginUserService loginService;
	
	@GetMapping(path="/firstApi")
	public String helloWorld() {
		return "Basic Rest API-";
	}
	
	@GetMapping(path="/firstApi-bean")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')") //use @EnableMethodSecurity on config class to enable this authorization
	public FirstBean firstBean() {
		return new FirstBean("Json Bean");
	}
	
	@GetMapping(path="/firstApi/{names}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public FirstBean firstBeanPathVariable(@PathVariable String names) {
		return new FirstBean("This is "+names);
	}
	
	@GetMapping(path="/firstApii18n")
	public String helloWorldi18n() {
		Locale locale = LocaleContextHolder.getLocale();
		return msource.getMessage("good.morning.message", null, "Default Message", locale);
	}
	
	@PostMapping(path="/loginUser")
	public String addNewLoginuser(@RequestBody LoginUsersInfo userInfo) {
		return loginService.addUser(userInfo);
	}

}
