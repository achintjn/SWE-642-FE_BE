package com.project.rest.webservices.restfuwebservices.jpadao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rest.webservices.restfuwebservices.user.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {

}
