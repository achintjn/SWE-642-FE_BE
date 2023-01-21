package com.project.rest.webservices.restfuwebservices.jpadao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rest.webservices.restfuwebservices.user.Posts;

public interface PostsRepository extends JpaRepository<Posts,Integer> {

}
