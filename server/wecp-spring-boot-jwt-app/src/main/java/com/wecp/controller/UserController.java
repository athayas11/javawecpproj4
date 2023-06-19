package com.wecp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController

//@CrossOrigin("*")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user)
			throws Exception {

		User usr = null
			if(usr == null) {
				
			}else {
				usr.setPassword(user.getPassword());			
			}
		Map<String, String> data = new HashMap<>();
		data.put("success", "User added successfully");
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<?> getAllUsers()
			throws Exception {

		List<User> users = repository.findAll();

		return ResponseEntity.ok(null);
	}

}
