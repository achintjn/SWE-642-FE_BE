package com.project.rest.webservices.restfuwebservices.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


@Entity(name = "user_details") //for JPA to know and manage this as repository
public class Users {
	
	@Id //for jpa identifier
	@GeneratedValue
	private Integer id;
	
	@Size(min=1, message="Name should have atleast 1 character")
	//@JsonProperty("user_name")
	private String name;
	
	@Past(message="Date should be in Past")
	//@JsonProperty("Date_Of_Birth")
	private LocalDate birthdate;
	
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Posts> posts;
	
	
	protected Users(){
		
	}
	
	public Users(Integer id, String name, LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	
	public List<Posts> getPosts() {
		return posts;
	}

	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", birthdate=" + birthdate + "]";
	}
	
	

}
