package com.project.rest.webservices.restfuwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	@GetMapping("/users")
	//@RequestMapping(method = RequestMethod.GET, value="/users", produces = {"application/json","application/xml"})
	public List<Users> retrieveAllUser(){
		return service.findAll();
	}
	
	//Entity Model -- an entity model wrapping a domain object and adding links to it.
	
	//WebMvcLinkBuilder -- add links to Entity Model
	
	
	@GetMapping("users/{id}")
	public EntityModel<Users> user(@PathVariable int id) {
		Users findOne = service.findOne(id);
		
		if(findOne==null)
			throw new UserNotFoundException("Id- "+id);
		
		EntityModel<Users> entityModel = EntityModel.of(findOne);
		
		//Hateoas
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUser());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
		Users _user = service.saveUser(user);
		// /users/4 ==> /users/{id}, user.getId()
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(_user.getId())
				.toUri(); 
		
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable int id) {
		Users findOne = service.deleteUserById(id);
		
		if(findOne==null)
			throw new UserNotFoundException("Id- "+id);
		
	}
}
