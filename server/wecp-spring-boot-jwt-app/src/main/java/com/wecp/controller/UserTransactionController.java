Can't create, edit, or upload … Not enough storage. Get more now, or remove files from Drive, Google Photos, or Gmail.
package com.wecp.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
import com.wecp.entities.UserTransaction;
import com.wecp.repos.UserRepository;
import com.wecp.repos.UserTransactionRepository;

@RestController
@CrossOrigin()
public class UserTransactionController {
	@Autowired
	UserTransactionRepository userTransactionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	@PreAuthorize("hasRole('CUSTOMER')")
	@Transactional
	public ResponseEntity<?> transact(@RequestBody UserTransaction transaction)
			throws Exception {

		Objects.requireNonNull(transaction);
		Objects.requireNonNull(transaction.getUserId());
		Objects.requireNonNull(transaction.getTransactionType());
		Objects.requireNonNull(transaction.getTransactionAmount());
		User uswr = userRepository.findByUserId(transaction.getUserId());
		Objects.requireNonNull(uswr);
		
			if(transaction.getTransactionType().equals("CREDIT")) {
				userTransactionRepository.save(transaction);
				uswr.setOustanding(uswr.getOustanding() + transaction.getTransactionAmount());
				userRepository.save(uswr);
			}
			else if(transaction.getTransactionType().equals("DEBIT")){
				if(uswr.getOustanding() == null) {
					throw new Exception("NOT_ENOUGH_BALANCE");
				}
				
				if(uswr.getOustanding() < transaction.getTransactionAmount()) {
					throw new Exception("NOT_ENOUGH_BALANCE");
				}
				
				uswr.setOustanding(uswr.getOustanding() - transaction.getTransactionAmount());
				userRepository.save(uswr);
				userTransactionRepository.save(transaction);
			}

		Map<String, String> data = new HashMap<>();
		data.put("success", "Transaction performed successfully");
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}