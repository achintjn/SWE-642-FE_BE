package com.project.rest.webservices.restfuwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	
	//URI
	@GetMapping("/v1/person")
	public PersonV1 getPersonV1() {
		return new PersonV1("Achint Jain");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2(new Name("Achint","Jain"));
	}
	
	
	//Request Parameter
	@GetMapping(path ="/person", params="version=1")
	public PersonV1 getPersonRequestparameter() {
		return new PersonV1("Achint Jain");
	}
	
	@GetMapping(path="/person", params="version=2")
	public PersonV2 getPersonRequestParameterV2() {
		return new PersonV2(new Name("Achint","Jain"));
	}
	
	
	//Request Header
	@GetMapping(path ="/person/header", headers="X-API-VERSION=1")
	public PersonV1 getPersonRequestHeader() {
		return new PersonV1("Achint Jain");
	}
	
	@GetMapping(path="/person/header", headers="X-API-VERSION=2")
	public PersonV2 getPersonRequestHeaderV2() {
		return new PersonV2(new Name("Achint","Jain"));
	}
	
	
	//Media Type
	@GetMapping(path ="/person/accept", produces="application/vnd.company.app-v1+json")
	public PersonV1 getPersonMediaType() {
		return new PersonV1("Achint Jain");
	}

	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v2+json")
	public PersonV2 getPersonediaTypeMV2() {
		return new PersonV2(new Name("Achint","Jain"));
	}
}
