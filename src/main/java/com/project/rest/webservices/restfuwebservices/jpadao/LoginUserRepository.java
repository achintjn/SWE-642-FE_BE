package com.project.rest.webservices.restfuwebservices.jpadao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.rest.webservices.restfuwebservices.user.LoginUsersInfo;

public interface LoginUserRepository extends JpaRepository<LoginUsersInfo,Integer> {

	Optional<LoginUsersInfo> findByName(String username);

}
