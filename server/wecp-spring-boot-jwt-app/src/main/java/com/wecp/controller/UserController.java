Can't create, edit, or upload … Not enough storage. Get more now, or remove files from Drive, Google Photos, or Gmail.
package com.wecp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wecp.entities.User;
import com.wecp.repos.UserRepository;

@RestController

//@CrossOrigin("*")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user)
			throws Exception {

		Objects.requireNonNull(user);
		Objects.requireNonNull(user.getUserId());
		Objects.requireNonNull(user.getPassword());
		User usr = repository.findByUserId(user.getUserId());
			if(usr == null) {
				repository.save(user);
			}else {
				usr.setPassword(user.getPassword());
				repository.save(usr);
			}
		Map<String, String> data = new HashMap<>();
		data.put("success", "User added successfully");
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers()
			throws Exception {

		List<User> users = repository.findAll();

		return ResponseEntity.ok(users);
	}

}