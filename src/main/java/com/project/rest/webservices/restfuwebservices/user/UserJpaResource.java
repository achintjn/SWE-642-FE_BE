package com.project.rest.webservices.restfuwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.project.rest.webservices.restfuwebservices.jpadao.PostsRepository;
import com.project.rest.webservices.restfuwebservices.jpadao.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PostsRepository postRepository;
	 
	
	@GetMapping("/jpa/users")
	//@RequestMapping(method = RequestMethod.GET, value="/users", produces = {"application/json","application/xml"})
	public List<Users> retrieveAllUser(){
		return repository.findAll();
	}
	
	//Entity Model -- an entity model wrapping a domain object and adding links to it.
	
	//WebMvcLinkBuilder -- add links to Entity Model
	@GetMapping("/jpa/users/{id}")
	public EntityModel<Users> user(@PathVariable int id) {
		Optional<Users> findOne = repository.findById(id);
		
		if(findOne.isEmpty())
			throw new UserNotFoundException("Id- "+id);
		
		EntityModel<Users> entityModel = EntityModel.of(findOne.get());
		
		//Hateoas
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUser());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
		Users _user = repository.save(user);
		// /users/4 ==> /users/{id}, user.getId()
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(_user.getId())
				.toUri(); 
		
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		 repository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Posts> getAllPostsForUser(@PathVariable int id) {
		Optional<Users> findOne  = repository.findById(id);
		
		if(findOne.isEmpty())
			throw new UserNotFoundException("Id- "+id);
		
		return findOne.get().getPosts();
	}
	
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostforUser(@PathVariable int id, @Valid @RequestBody Posts posts) {
		Optional<Users> findOne  = repository.findById(id);
		
		if(findOne.isEmpty())
			throw new UserNotFoundException("Id- "+id);
		posts.setUser(findOne.get());
		
		@Valid
		Posts savedPost = postRepository.save(posts);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri(); 
		
		return ResponseEntity.created(location).build();
	}
}
