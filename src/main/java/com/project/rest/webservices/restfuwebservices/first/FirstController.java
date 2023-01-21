package com.project.rest.webservices.restfuwebservices.first;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
	
	// /basic api
	
//	@RequestMapping(method=RequestMethod.GET, path="/firstApi")
//	public String helloWorld() {
//		return "Basic Rest API";
//	}
	
	@Autowired
	private MessageSource msource;
	
	@GetMapping(path="/firstApi")
	public String helloWorld() {
		return "Basic Rest API-";
	}
	
	@GetMapping(path="/firstApi-bean")
	public FirstBean firstBean() {
		return new FirstBean("Json Bean");
	}
	
	@GetMapping(path="/firstApi/{names}")
	public FirstBean firstBeanPathVariable(@PathVariable String names) {
		return new FirstBean("This is "+names);
	}
	
	@GetMapping(path="/firstApii18n")
	public String helloWorldi18n() {
		Locale locale = LocaleContextHolder.getLocale();
		return msource.getMessage("good.morning.message", null, "Default Message", locale);
	}

}
